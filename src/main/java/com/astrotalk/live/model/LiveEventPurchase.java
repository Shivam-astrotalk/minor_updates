package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "live_event_purchase")
public class LiveEventPurchase {

    @Id
    @GeneratedValue
    Long id;

    long userId;
    long eventId;
    long productId;
    int amount;
    String description;
    long creationTime;
}
