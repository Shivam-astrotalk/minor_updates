package com.astrotalk.live.service;

import com.astrotalk.live.model.*;
import com.astrotalk.live.repository.LiveEventActivityRepository;
import com.astrotalk.live.repository.LiveEventBlockRepository;
import com.astrotalk.live.repository.LiveEventRepository;
import com.astrotalk.live.repository.LiveEventSubscriberRepository;
import com.astrotalk.live.youtube.YoutubeUtils;
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
    LiveEventRepository liveEventRepository;

    @Autowired
    LiveEventSubscriberRepository liveEventSubscriberRepository;

    @Autowired
    LiveEventBlockRepository liveEventBlockRepository;

    @Autowired
    LiveEventActivityRepository liveEventActivityRepository;


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

    public List<LiveEvent> getAllByStatus(Status status, long fromId, long astrologerId, int pageSize){
        if(astrologerId != -1)
            return liveEventRepository.getAllByStatus(status.name(),fromId,pageSize);
        else
            return liveEventRepository.getAllByStatusOfAstrologer(status.name(),fromId,pageSize,astrologerId);
    }

    private boolean isBlocked(long userId, long eventId){
        List<LiveEventBlock> blockList = liveEventBlockRepository.getBlocksByEventId(eventId);
        for(LiveEventBlock block : blockList){
            if(block.getUserId() == userId)
                return true;
        }
        return false;
    }
    public void joinEvent(long userId, long eventId, String userName) throws LiveException {
        if(isBlocked(userId,eventId))
            throw new LiveException("User is blocked for this event");

        List<LiveEventSubscriber> liveEventSubscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        for(LiveEventSubscriber subscriber : liveEventSubscribers){
            if(subscriber.getUserId() == userId && subscriber.getLeaveTime() == null) {
                subscriber.setLeaveTime(new Date());
                liveEventSubscriberRepository.save(subscriber);
            }
        }
        LiveEventSubscriber liveEventSubscriber = new LiveEventSubscriber();
        liveEventSubscriber.setJoinTime(new Date());
        liveEventSubscriber.setLiveEventId(eventId);
        liveEventSubscriber.setUserId(userId);
        liveEventSubscriber.setUserName(userName);
        liveEventSubscriberRepository.save(liveEventSubscriber);

        LiveEventActivity liveEventActivity = new LiveEventActivity();
        liveEventActivity.setEventId(eventId);
        liveEventActivity.setActivity(userName + " joined");
        liveEventActivityRepository.save(liveEventActivity);
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
                subscriber.setLeaveTime(new Date());
                liveEventSubscriberRepository.save(subscriber);
            }
        }
    }

    public List<LiveEventSubscriber> getCurrentSubscribers(long eventId) {
        List<LiveEventSubscriber> subscribers = liveEventSubscriberRepository.getSubscribers(eventId);
        subscribers = subscribers.stream().filter(liveEventSubscriber -> liveEventSubscriber.getLeaveTime() != null).collect(Collectors.toList());
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

    public LiveEvent goLive(long eventId, String authCode) throws IOException, GeneralSecurityException {
        LiveEvent liveEvent = liveEventRepository.findById(eventId).get();
        if(!liveEvent.getStatus().equals(Status.APPROVED))
            return liveEvent;
        YoutubeUtils.goLive(authCode,liveEvent);
        liveEvent.setStatus(Status.ONGOING);
        return liveEvent;
    }
}
