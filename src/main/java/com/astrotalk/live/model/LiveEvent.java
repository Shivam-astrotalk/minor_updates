package com.astrotalk.live.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "live_event")
public class LiveEvent {

    @Id
    @GeneratedValue
    Long id;

    long astrologerId;
    String astrologerName;
    String astrologerPic;

    long estimatedStartTime;
    long actualStartTime;

    long estimatedEndTime;
    long actualEndTime;

    int entryFee;

    @Column(name = "ingestion_url",columnDefinition = "TEXT")
    String ingestionUrl;

    @Column(name = "consumption_url",columnDefinition = "TEXT")
    String consumptionUrl;

    @Enumerated(EnumType.STRING)
    Status status;

    String title;
    String description;


    @Transient
    boolean currentUserBooked;

}
