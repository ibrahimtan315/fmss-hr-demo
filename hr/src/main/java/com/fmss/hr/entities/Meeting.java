package com.fmss.hr.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="meeting")
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="meeting_id")
    private Long meeting_id;
    @Column(length = 5000)
    private String start_url;
    @Column
    private String host_id;
    @Column
    private String host_email;
    @Column
    private String topic;
    @Column
    private String status;
    @Column
    private LocalDateTime start_time;
    @Column
    private int duration;
    @Column
    private String timezone;
    @Column(name = "meetingLink", length = 5000)
    private String meetingLink;

}
