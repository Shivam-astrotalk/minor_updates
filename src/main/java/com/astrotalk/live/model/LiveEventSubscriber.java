package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "live_event_subscriber")
public class LiveEventSubscriber {

    @Id
    @GeneratedValue
    Long id;

    long liveEventId;
    long userId;
    String userName;
    long joinTime;
    long leaveTime;

    @Transient
    double amount;
}
