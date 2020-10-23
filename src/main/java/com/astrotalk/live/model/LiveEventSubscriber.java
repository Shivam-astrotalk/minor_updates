package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    Date joinTime;
    Date leaveTime;

}
