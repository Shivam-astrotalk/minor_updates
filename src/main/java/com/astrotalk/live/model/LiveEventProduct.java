package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "live_event_product")
public class LiveEventProduct {

    @Id
    @GeneratedValue
    Long id;

    long liveEventType;
    String productName;
    String productDescription;
    int price;

}
