
package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmss.hr.entities.ConfirmationStatus;
import com.fmss.hr.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "spending_statement")
    private String spendingStatement;

    @Column(name = "expenditure_type")
    private String expenditureType;

    @Column(name = "spending_amount")
    private BigDecimal spendingAmount;

    @Column(name = "status")
    private Boolean status;

    @Column(name="expense_entry_time")
    private LocalDateTime expenseEntryTime;

    @Column(name = "expenseTime")
    private LocalDate expenseTime;

    @Column(name = "expensePath")
    private String expensePath;

    @Enumerated(EnumType.STRING)
    @Column(name="confirmation")
    private ConfirmationStatus confirmation = ConfirmationStatus.PENDING;

    @Column(name = "manager_id")
    private Long manager_id;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @Column(name="tax")
    private BigDecimal tax;


}

