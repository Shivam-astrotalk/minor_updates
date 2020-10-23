package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "live_event_notify")
public class LiveEventNotify {

    @Id
    @GeneratedValue
    Long id;

    long liveEventId;
    long userId;

}
