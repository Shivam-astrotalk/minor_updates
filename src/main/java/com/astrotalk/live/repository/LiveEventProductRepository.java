package com.astrotalk.live.repository;

import com.astrotalk.live.model.LiveEventActivity;
import com.astrotalk.live.model.LiveEventProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveEventProductRepository extends CrudRepository<LiveEventProduct,Long> {

    @Query(value = "Select * from live_event_product where active = true", nativeQuery = true)
    List<LiveEventProduct> getAllActiveProducts();

}
