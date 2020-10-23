package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "live_event_activity")
public class LiveEventActivity {

    @Id
    @GeneratedValue
    Long id;

    long eventId;

    @Column(columnDefinition = "TEXT")
    String activity;

}
