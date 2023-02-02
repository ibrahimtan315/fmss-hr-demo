package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "overtime")
@Data
public class Overtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="date")
    private LocalDate date;

    @Column(name="hour")
    private int hour;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "overtimeDescription")
    private String overtimeDescription;

    @Column(name="overtime_entry_time")
    private LocalDateTime overtimeEntryTime;

    @ManyToOne
    @JoinColumn(name="userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="confirmation")
    private ConfirmationStatus confirmation = ConfirmationStatus.PENDING ;

    @Column(name = "manager_id")
    private Long manager_id;

}
