
package com.fmss.hr.dto.projection;

import com.fmss.hr.entities.ConfirmationStatus;

import java.math.BigDecimal;

public interface ExpenseUserProjection {
    String getSpendingStatement();
    String getExpenditureType();
    BigDecimal getSpendingAmount();
    Boolean getStatus();
    Long getId();
    String getExpencePath();
    ConfirmationStatus getConfirmation();
}

