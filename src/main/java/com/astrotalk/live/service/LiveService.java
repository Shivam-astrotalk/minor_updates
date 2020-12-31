package com.astrotalk.live.service;

import com.astrotalk.live.Timings;
import com.astrotalk.live.agora.RtcTokenBuilder;
import com.astrotalk.live.model.*;
import com.astrotalk.live.network.WalletServiceClient;
import com.astrotalk.live.repository.*;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LiveService {

    @Autowired
    LiveEventProductRepository productRepository;

    @Autowired
    LiveEventPurchaseRepository purchaseRepository;

    @Autowired
    LiveEventRepository liveEventRepository;

    @Autowired
    LiveEventSubscriberRepository liveEventSubscriberRepository;

    @Autowired
    LiveEventBlockRepository liveEventBlockRepository;

    @Autowired
    LiveEventActivityRepository liveEventActivityRepository;

    @Autowired
    RtcTokenBuilder tokenBuilder;

    @Autowired
    WalletServiceClient walletServiceClient;

    @Value("${WALLET_SERVICE_SECRET_KEY}")
    String walletSecretKey;

    public void create(LiveEvent liveEvent){
        liveEvent.setStatus(Status.CREATED);
        liveEventRepository.save(liveEvent);
    }

    public void update(LiveEvent liveEvent){
        liveEventRepository.save(liveEvent);
    }


    public LiveEvent getById(long eventId){
        return liveEventRepository.findById(eventId).get();
    }

    public void updateStatus(long liveEventId, Status status){
        LiveEvent liveEvent = liveEventRepository.findById(liveEventId).get();
        liveEvent.setStatus(status);
        log.info("Status : {}", status);
        log.info("Updating status {}", liveEvent);
        liveEventRepository.save(liveEvent);
    }

    public void updateLink(long eventId, String link, boolean goLive){
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        liveEvent.setConsumptionUrl(link);
        if(goLive)
            liveEvent.setStatus(Status.ONGOING);
        liveEventRepository.save(liveEvent);
    }

    public List<LiveEvent> getAllByStatus(Status status, long fromId, long astrologerId, int pageSize){
        if(astrologerId == -1)
            return liveEventRepository.getAllByStatus(status.name(),fromId,pageSize);
        else
            return liveEventRepository.getAllByStatusOfAstrologer(status.name(),fromId,pageSize,astrologerId);
    }
    public List<LiveEvent> getAllByAstrologer(long astrologerId){
        return liveEventRepository.getAllByAstrologer(astrologerId);
    }

    public List<LiveEvent> getAllForUser(long userId){
        List<LiveEvent> liveEvents = liveEventRepository.getAllForUser();
        List<LiveEventSubscriber> liveEventSubscribers = liveEventSubscriberRepository.getUserSubscriptions(userId);
        HashMap<Long,LiveEvent> liveEventHashMap = new HashMap<>();
        for(LiveEvent liveEvent : liveEvents){
            liveEventHashMap.put(liveEvent.getId(),liveEvent);
        }
        for(LiveEventSubscriber liveEventSubscriber : liveEventSubscribers){
            LiveEvent liveEvent = liveEventHashMap.get(liveEventSubscriber.getLiveEventId());
            if(liveEvent != null)
                liveEvent.setCurrentUserBooked(true);
        }
        return liveEvents;
    }
    private boolean isBlocked(long userId, long eventId){
        List<LiveEventBlock> blockList = liveEventBlockRepository.getBlocksByEventId(eventId);
        for(LiveEventBlock block : blockList){
            if(block.getUserId() == userId)
                return true;
        }
        return false;
    }

    @Transactional
    public String joinEvent(long userId, long eventId, String userName, String userPic) {
        if (isBlocked(userId, eventId))
            throw new LiveException("User is blocked for this event");

        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        List<LiveEventSubscriber> liveEventSubscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        boolean alreadySubscribered = false;
        for (LiveEventSubscriber subscriber : liveEventSubscribers) {
            if (subscriber.getUserId() == userId) {
                alreadySubscribered = true;
            }
        }
        if (!alreadySubscribered ) {
            log.info("Subscribing to eventId {}", eventId);
            LiveEventSubscriber liveEventSubscriber = new LiveEventSubscriber();
            liveEventSubscriber.setJoinTime(System.currentTimeMillis());
            liveEventSubscriber.setLiveEventId(eventId);
            liveEventSubscriber.setUserId(userId);
            liveEventSubscriber.setUserName(userName);
            liveEventSubscriber.setUserPic(userPic);
            liveEventSubscriberRepository.save(liveEventSubscriber);
            LiveEventPurchase purchase = new LiveEventPurchase();
            purchase.setAmount(liveEvent.getEntryFee());
            purchase.setUserId(userId);
            purchase.setProductId(-1);
            purchase.setCreationTime(Timings.currentTimeIndia());
            purchase.setEventId(eventId);
            purchaseRepository.save(purchase);
            log.info("Booking purchase {}", purchase);
            checkAndDeductMoney(userId, liveEvent.getEntryFee(), "Joining event : " + liveEvent.getTitle(),liveEvent.getId(), purchase.getId());
        }
        if(liveEvent.getStatus().equals(Status.ONGOING)){
            LiveEventActivity liveEventActivity = new LiveEventActivity();
            liveEventActivity.setEventId(eventId);
            liveEventActivity.setActivity("joined");
            liveEventActivity.setUserName(userName);
            liveEventActivity.setUserPic(userPic);
            liveEventActivityRepository.save(liveEventActivity);
            return tokenBuilder.buildTokenWithUserAccount(String.valueOf(eventId), RtcTokenBuilder.Role.Role_Subscriber,0);
        }
        return null;
    }

    public void blockUser(long userId, long eventId){
        LiveEventBlock liveEventBlock = new LiveEventBlock();
        liveEventBlock.setUserId(userId);
        liveEventBlock.setLiveEventId(eventId);
        liveEventBlockRepository.save(liveEventBlock);
        leaveEvent(userId,eventId);
    }

    public void leaveEvent(long userId, long eventId){
        List<LiveEventSubscriber> subscribers = liveEventSubscriberRepository.getSubscribers(eventId,userId);
        for(LiveEventSubscriber subscriber : subscribers){
            if(subscriber.getLeaveTime() != 0) {
                subscriber.setLeaveTime(System.currentTimeMillis());
                liveEventSubscriberRepository.save(subscriber);
            }
        }
    }

    public List<LiveEventSubscriber> getCurrentSubscribers(long eventId) {
        List<LiveEventSubscriber> subscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        //subscribers = subscribers.stream().filter(l -> l.getLeaveTime() == 0).collect(Collectors.toList());
        //TODO : add amount;
        List<LiveEventPurchase> purchases = purchaseRepository.getAllPurchases(eventId);
        purchases = purchases.stream().filter(p -> p.getProductId() != -1).collect(Collectors.toList());
        HashMap<Long,Integer> purchaseMap = new HashMap<>();
        for(LiveEventPurchase purchase : purchases){
            Integer amount = purchaseMap.get(purchase.getUserId());
            if(amount == null)
                amount = purchase.getAmount();
            else
                amount += purchase.getAmount();
            purchaseMap.put(purchase.getUserId(),amount);
        }
        for(LiveEventSubscriber liveEventSubscriber : subscribers){
            Integer amount = purchaseMap.get(liveEventSubscriber.getUserId());
            if(amount != null)
                liveEventSubscriber.setAmount(amount);
        }
        return subscribers;
    }

    public List<LiveEventActivity> getActivity(long fromId,long eventId, long userId) throws LiveException {
        if(!isBlocked(userId,eventId))
            return liveEventActivityRepository.getActivityAfterId(eventId,fromId);
        else
            throw new LiveException("User is blocked");
    }

    public void addMessageActivity(long userId, long eventId, String username, String userPic,String message) throws LiveException {
        if(isBlocked(userId,eventId))
            throw new LiveException("User is blocked");
        LiveEventActivity liveEventActivity = new LiveEventActivity();
        liveEventActivity.setActivity(message);
        liveEventActivity.setUserPic(userPic);
        liveEventActivity.setUserName(username);
        liveEventActivity.setEventId(eventId);
        liveEventActivityRepository.save(liveEventActivity);
    }

    public String goLive(long eventId) throws IOException, GeneralSecurityException {
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        if(!liveEvent.getStatus().equals(Status.APPROVED) && !liveEvent.getStatus().equals(Status.ONGOING))
            return null;
        String token = tokenBuilder.buildTokenWithUserAccount(String.valueOf(eventId), RtcTokenBuilder.Role.Role_Publisher,0);
        liveEvent.setStatus(Status.ONGOING);
        liveEvent.setActualStartTime(System.currentTimeMillis());
        liveEventRepository.save(liveEvent);
        return token;
    }

    public void endEvent(long eventId){
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        liveEvent.setStatus(Status.FINISHED);
        liveEvent.setActualEndTime(Timings.currentTimeIndia());
        liveEventRepository.save(liveEvent);
        // TODO : calculate astrologer & astrotalk cut.
    }


    public void checkAndDeductMoney(long userId, double money, String message, long eventId, long purchaseId) throws LiveException {
        //throw new LiveException("Not enough money, please recharge");
        Call<com.astrotalk.live.model.pojo.Response> call = walletServiceClient.deductUserWallet(-1.0*money, URLEncoder.encode(message),purchaseId,walletSecretKey,"6",userId);
        try{
            Response<com.astrotalk.live.model.pojo.Response> response = call.execute();
            log.info("Response : {}", response);
            com.astrotalk.live.model.pojo.Response s = response.body();
            log.info("parsed response : {}",s);
            if(!s.getStatus().equals("success"))
                throw new LiveException(s.getReason());
        }catch (Exception exception){
            exception.printStackTrace();
            throw new LiveException(exception.getMessage());
        }
    }


    public void saveOrUpdate(LiveEventProduct product){
        productRepository.save(product);
    }

    public List<LiveEventProduct> getAllProducts(){
       return productRepository.getAllActiveProducts();
    }

    @Transactional(rollbackFor = LiveException.class)
    public void buyProduct(long userId, long eventId, long productId, String userName, String userPic){
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        LiveEventProduct liveEventProduct = productRepository.findById(productId).get();

        LiveEventPurchase purchase = new LiveEventPurchase();
        purchase.setAmount(liveEventProduct.getPrice());
        purchase.setUserId(userId);
        purchase.setProductId(productId);
        purchase.setCreationTime(Timings.currentTimeIndia());
        purchase.setEventId(eventId);
        LiveEventActivity activity = new LiveEventActivity();
        activity.setActivity("donated " + liveEventProduct.getProductName());
        activity.setUserPic(userPic);
        activity.setUserName(userName);
        activity.setEventId(eventId);
        liveEventActivityRepository.save(activity);
        purchaseRepository.save(purchase);
        checkAndDeductMoney(userId,liveEventProduct.getPrice(),"Purchased " + liveEventProduct.getProductName() + " in eventId " + eventId, eventId, purchase.getId());
    }

    public List<LiveEvent> getUserHistory(long userId){
        List<LiveEventSubscriber> liveEventSubscriber = liveEventSubscriberRepository.getUserSubscriptions(userId);
        List<Long> eventIds = liveEventSubscriber.stream().map(s -> s.getLiveEventId()).collect(Collectors.toList());
        List<LiveEvent> liveEvents = new ArrayList<>();
        for(LiveEvent liveEvent : liveEventRepository.findAllById(eventIds)){
            liveEvents.add(liveEvent);
        }
        Collections.sort(liveEvents, new Comparator<LiveEvent>() {
            @Override
            public int compare(LiveEvent o1, LiveEvent o2) {
                if(o2.getEstimatedStartTime() > o1.getEstimatedStartTime())
                    return -1;
                else
                    return 1;
            }
        });
        return liveEvents;
    }
}
