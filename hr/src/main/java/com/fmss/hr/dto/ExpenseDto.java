

package com.fmss.hr.dto;

import com.fmss.hr.entities.ConfirmationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ExpenseDto {

    private String firstName;
    private String lastName;
    private String IdentityNumber;
    private String spendingStatement;
    private String expenditureType;
    private BigDecimal spendingAmount;
    private LocalDate expenseTime;
    private Boolean status;
    private Long userId;
    private Long id;
    private ConfirmationStatus confirmation;
    private String expensePath;
    private LocalDateTime expenseEntryTime;

    public ExpenseDto(String firstName, String lastName, String identityNumber, String spendingStatement,
                      String expenditureType, BigDecimal spendingAmount, Boolean status, Long id , Long userId ,
                      ConfirmationStatus confirmation , LocalDate expenseTime ,String expensePath) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.IdentityNumber=identityNumber;
        this.spendingStatement=spendingStatement;
        this.expenseTime=expenseTime;
        this.expenditureType=expenditureType;
        this.spendingAmount=spendingAmount;
        this.status=status;
        this.id=id;
        this.userId=userId;
        this.confirmation=confirmation;
        this.expensePath=expensePath;
    }
}

