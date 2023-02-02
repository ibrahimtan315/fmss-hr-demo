package com.fmss.hr.mapper;

import com.fmss.hr.dto.ExpenseDto;
import com.fmss.hr.dto.projection.ExpenseProjection;

import com.fmss.hr.entities.Expense;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDto expenseToExpenseDto(Expense expense);
    ExpenseDto expenseProjectionToExpenseDto(ExpenseProjection expenseProjection);
    List<ExpenseDto> expenseListToExpenseDtoList(List<Expense> expenseList);
    Expense expenseDtotoExpense(ExpenseDto expenseDto);

}
