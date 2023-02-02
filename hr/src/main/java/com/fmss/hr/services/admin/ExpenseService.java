package com.fmss.hr.services.admin;

import com.fmss.hr.dto.ExpenseDto;
import com.fmss.hr.entities.Expense;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses();
    List<ExpenseDto> getFindAllExpenseUsers();
    ExpenseDto createExpense(ExpenseDto expenseDto);
    List<ExpenseDto> getAllExpenseByUserId(Long id);
    ExpenseDto approveExpense(Long id);
    ExpenseDto rejectExpense(Long id);
    List<ExpenseDto> getPendingExpensesByManagerId(Long managerId);
}
