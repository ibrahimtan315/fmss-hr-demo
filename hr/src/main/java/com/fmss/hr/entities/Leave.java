package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "leave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "total_leave")
    private Long totalLeave;

    @Column(name = "leave_start_time")
    private LocalDate leaveStartTime;

    @Column(name = "leave_start_c")
    private LocalDateTime leaveStartC;

    @Column(name = "leave_end_c")
    private LocalDateTime leaveEndC;

    @Column(name = "leave_end_time")
    private LocalDate leaveEndTime;

    @Column(name="leave_entry_time")
    private LocalDateTime leaveEntryTime;

    @Column(name = "leave_statement")
    private String leaveStatement;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="confirmation")
    private ConfirmationStatus confirmation = ConfirmationStatus.PENDING ;

    @Column(name = "manager_id")
    private Long manager_id;


    public Leave(Long id, String leaveType, Long totalLeave, LocalDate leaveStartTime,
                 LocalDateTime leaveStartC, LocalDateTime leaveEndC, LocalDate leaveEndTime,
                 String leaveStatement, LocalDate returnDate, Boolean status, User user,
                 ConfirmationStatus confirmation, Long manager_id) {
        this.id = id;
        this.leaveType = leaveType;
        this.totalLeave = totalLeave;
        this.leaveStartTime = leaveStartTime;
        this.leaveStartC = leaveStartC;
        this.leaveEndC = leaveEndC;
        this.leaveEndTime = leaveEndTime;
        this.leaveStatement = leaveStatement;
        this.returnDate = returnDate;
        this.status = status;
        this.user = user;
        this.confirmation = confirmation;
        this.manager_id = manager_id;
    }
}
