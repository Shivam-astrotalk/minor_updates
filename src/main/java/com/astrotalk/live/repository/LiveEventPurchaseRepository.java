package com.astrotalk.live.repository;

import com.astrotalk.live.model.LiveEventProduct;
import com.astrotalk.live.model.LiveEventPurchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveEventPurchaseRepository extends CrudRepository<LiveEventPurchase,Long> {

    @Query(value = "Select * from live_event_purchase where event_id = ?1", nativeQuery = true)
    List<LiveEventPurchase> getAllPurchases(long eventId);

}
