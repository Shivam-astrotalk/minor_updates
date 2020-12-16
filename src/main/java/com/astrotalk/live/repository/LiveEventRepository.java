package com.astrotalk.live.repository;

import com.astrotalk.live.model.LiveEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveEventRepository extends CrudRepository<LiveEvent,Long> {

    @Query(value = "Select * from live_event where id > ?2 and status = ?1 limit ?3", nativeQuery = true)
    List<LiveEvent> getAllByStatus(String status, long fromId, int pageSize);

    @Query(value = "Select * from live_event where id > ?2 and astrologer_id = ?4 and status = ?1 limit ?3", nativeQuery = true)
    List<LiveEvent> getAllByStatusOfAstrologer(String status, long fromId, int pageSize,long astrologerId);

    @Query(value = "Select * from live_event where astrologer_id = ?1", nativeQuery = true)
    List<LiveEvent> getAllByAstrologer(long astrologerId);

}
