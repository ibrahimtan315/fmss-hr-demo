
package com.fmss.hr.dto.projection;

import com.fmss.hr.entities.ConfirmationStatus;
import com.fmss.hr.entities.User;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExpenseProjection {
    String getFirstName();
    String getLastName();
    String getIdentityNumber();
    String getSpendingStatement();
    String getExpenditureType();
    BigDecimal getSpendingAmount();
    Boolean getStatus();
    Long getId();
    ConfirmationStatus getConfirmation();
    String getExpensePath();
    LocalDate getExpenseTime();
    @Value("#{target.id}")
    Long getUserId();
}

