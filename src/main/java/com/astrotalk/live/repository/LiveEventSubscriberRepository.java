package com.astrotalk.live.repository;

import com.astrotalk.live.model.LiveEventSubscriber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LiveEventSubscriberRepository extends CrudRepository<LiveEventSubscriber,Long> {

    @Query(value = "Select * from live_event_subscriber where live_event_id = ?1",nativeQuery = true)
    List<LiveEventSubscriber> getSubscribers(long eventId);

    @Query(value = "Select * from live_event_subscriber where live_event_id = ?1 and user_id = ?2", nativeQuery = true)
    List<LiveEventSubscriber> getSubscribers(long eventId, long userId);

    @Query(value = "Select * from live_event_subscriber where user_id = ?2", nativeQuery = true)
    List<LiveEventSubscriber> getUserSubscriptions(long userId);
}
