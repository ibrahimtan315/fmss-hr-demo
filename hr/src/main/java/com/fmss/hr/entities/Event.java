package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "candidateId")
    private Long candidateId;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "eventDescription")
    private String eventDescription;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    @Column(name = "isOnline")
    private Boolean isOnline;


    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private EventStatus status ;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name="meeting_id")
    private Meeting meeting;

    /*@OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<EventUser> eventUser;*/

}
