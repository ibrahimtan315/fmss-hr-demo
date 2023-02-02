package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "time_sheets")
public class TimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month")
    private String month;

    @Column(name="day")
    private int day;

    @Column(name = "year")
    private int year;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "time_spent")
    private int timeSpent;

    @OneToMany(mappedBy = "timeSheet")
    @JsonBackReference
    private List<SheetDetails> sheetDetails;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @Column(name = "isFilled")
    private Boolean isFilled;

    @Column(name = "isHoliday")
    private Boolean isHoliday;
}
