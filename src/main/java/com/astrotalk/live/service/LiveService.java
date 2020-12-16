package com.astrotalk.live.service;

import com.astrotalk.live.Timings;
import com.astrotalk.live.agora.RtcTokenBuilder;
import com.astrotalk.live.model.*;
import com.astrotalk.live.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
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


    public void create(LiveEvent liveEvent){
        liveEvent.setStatus(Status.CREATED);
        liveEventRepository.save(liveEvent);
    }

    public LiveEvent getById(long eventId){
        return liveEventRepository.findById(eventId).get();
    }

    public void updateStatus(long liveEventId, Status status){
        LiveEvent liveEvent = liveEventRepository.findById(liveEventId).get();
        liveEvent.setStatus(status);
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
        if(astrologerId != -1)
            return liveEventRepository.getAllByStatus(status.name(),fromId,pageSize);
        else
            return liveEventRepository.getAllByStatusOfAstrologer(status.name(),fromId,pageSize,astrologerId);
    }
    public List<LiveEvent> getAllByAstrologer(long astrologerId){
        return liveEventRepository.getAllByAstrologer(astrologerId);
    }

    private boolean isBlocked(long userId, long eventId){
        List<LiveEventBlock> blockList = liveEventBlockRepository.getBlocksByEventId(eventId);
        for(LiveEventBlock block : blockList){
            if(block.getUserId() == userId)
                return true;
        }
        return false;
    }
    public String joinEvent(long userId, long eventId, String userName) throws LiveException {
        if (isBlocked(userId, eventId))
            throw new LiveException("User is blocked for this event");

        List<LiveEventSubscriber> liveEventSubscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        boolean alreadySubscribered = false;
        for (LiveEventSubscriber subscriber : liveEventSubscribers) {
            if (subscriber.getUserId() == userId) {
                alreadySubscribered = true;
            }
        }
        if (alreadySubscribered) {
            return tokenBuilder.buildTokenWithUserAccount(String.valueOf(eventId), RtcTokenBuilder.Role.Role_Subscriber);
        } else {
            LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
            checkAndDeductMoney(userId, liveEvent.getEntryFee(), "Joining event : " + liveEvent.getTitle());
            LiveEventSubscriber liveEventSubscriber = new LiveEventSubscriber();
            liveEventSubscriber.setJoinTime(new Date(Timings.currentTimeIndia()));
            liveEventSubscriber.setLiveEventId(eventId);
            liveEventSubscriber.setUserId(userId);
            liveEventSubscriber.setUserName(userName);
            liveEventSubscriberRepository.save(liveEventSubscriber);

            LiveEventActivity liveEventActivity = new LiveEventActivity();
            liveEventActivity.setEventId(eventId);
            liveEventActivity.setActivity(userName + " joined");
            liveEventActivityRepository.save(liveEventActivity);
            LiveEventPurchase purchase = new LiveEventPurchase();
            purchase.setAmount(liveEvent.getEntryFee());
            purchase.setUserId(userId);
            purchase.setProductId(-1);
            purchase.setCreationTime(Timings.currentTimeIndia());
            purchase.setEventId(eventId);
            purchaseRepository.save(purchase);
            return tokenBuilder.buildTokenWithUserAccount(String.valueOf(eventId), RtcTokenBuilder.Role.Role_Subscriber);
        }
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
            if(subscriber.getLeaveTime() == null) {
                subscriber.setLeaveTime(new Date(Timings.currentTimeIndia()));
                liveEventSubscriberRepository.save(subscriber);
            }
        }
    }

    public List<LiveEventSubscriber> getCurrentSubscribers(long eventId) {
        List<LiveEventSubscriber> subscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        subscribers = subscribers.stream().filter(l -> l.getLeaveTime() != null).collect(Collectors.toList());
        return subscribers;
    }

    public List<LiveEventActivity> getActivity(long fromId,long eventId, long userId) throws LiveException {
        if(!isBlocked(userId,eventId))
            return liveEventActivityRepository.getActivityAfterId(eventId,fromId);
        else
            throw new LiveException("User is blocked");
    }

    public void addMessageActivity(long userId, long eventId, String username, String message) throws LiveException {
        if(isBlocked(userId,eventId))
            throw new LiveException("User is blocked");
        LiveEventActivity liveEventActivity = new LiveEventActivity();
        liveEventActivity.setActivity(username + ": " + message);
        liveEventActivity.setEventId(eventId);
        liveEventActivityRepository.save(liveEventActivity);
    }

    public String goLive(long eventId) throws IOException, GeneralSecurityException {
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        if(!liveEvent.getStatus().equals(Status.APPROVED))
            return null;
        String token = tokenBuilder.buildTokenWithUserAccount(String.valueOf(eventId), RtcTokenBuilder.Role.Role_Publisher);
        liveEvent.setStatus(Status.ONGOING);
        liveEvent.setActualStartTime(new Date(Timings.currentTimeIndia()));
        liveEventRepository.save(liveEvent);
        return token;
    }

    public void endEvent(long eventId){
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        liveEvent.setStatus(Status.FINISHED);
        liveEvent.setActualEndTime(new Date(Timings.currentTimeIndia()));
        liveEventRepository.save(liveEvent);
    }


    public void checkAndDeductMoney(long userId, double money, String message) throws LiveException {
        throw new LiveException("Not enough money, please recharge");
    }


    public void saveOrUpdate(LiveEventProduct product){
        productRepository.save(product);
    }

    public List<LiveEventProduct> getAllProducts(){
       return productRepository.getAllActiveProducts();
    }

    public void buyProduct(long userId, long eventId, long productId) throws LiveException {
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        LiveEventProduct liveEventProduct = productRepository.findById(productId).get();
        checkAndDeductMoney(userId,liveEventProduct.getPrice(),"Purchased " + liveEventProduct.getProductName() + " in eventId " + eventId);
        LiveEventPurchase purchase = new LiveEventPurchase();
        purchase.setAmount(liveEventProduct.getPrice());
        purchase.setUserId(userId);
        purchase.setProductId(productId);
        purchase.setCreationTime(Timings.currentTimeIndia());
        purchase.setEventId(eventId);
        purchaseRepository.save(purchase);
    }
}
