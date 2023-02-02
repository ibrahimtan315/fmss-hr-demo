package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "sheetdetails")
public class SheetDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_spent")
    private int timeSpent;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JsonManagedReference
    private TimeSheet timeSheet;

    @ManyToOne
    @JsonManagedReference
    private Task task;
}
