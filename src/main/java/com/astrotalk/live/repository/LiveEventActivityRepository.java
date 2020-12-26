package com.astrotalk.live.repository;

import com.astrotalk.live.model.LiveEventActivity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LiveEventActivityRepository extends CrudRepository<LiveEventActivity,Long> {

    @Query(value = "Select * from live_event_activity where id > ?2 and event_id = ?1 order by id desc", nativeQuery = true)
    List<LiveEventActivity> getActivityAfterId(long eventId, long afterId);

}
