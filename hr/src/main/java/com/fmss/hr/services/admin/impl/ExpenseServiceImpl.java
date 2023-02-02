

package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.ExpenseDto;
import com.fmss.hr.dto.projection.ExpenseProjection;
import com.fmss.hr.dto.projection.ExpenseUserProjection;
import com.fmss.hr.entities.ConfirmationStatus;
import com.fmss.hr.entities.Department;
import com.fmss.hr.entities.Expense;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.ExpenseMapper;
import com.fmss.hr.repos.admin.ExpenseRepository;
import com.fmss.hr.repos.user.UserRepository;
import com.fmss.hr.services.admin.ExpenseService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("expenseService")
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;
    private final DepartmentServiceImpl departmentService;

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<ExpenseDto> getFindAllExpenseUsers() {
        List<ExpenseProjection> expenseProjections = expenseRepository.findAllExpenseUsers();
        return expenseProjections
                .stream()
                .map(expenseMapper::expenseProjectionToExpenseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        LocalDateTime currentDate = LocalDateTime.now();
        expenseDto.setConfirmation(ConfirmationStatus.PENDING);
        expenseDto.setExpenseEntryTime(currentDate);
        Expense expense=expenseMapper.expenseDtotoExpense(expenseDto);
        User user = userRepository.findById(expenseDto.getUserId()).get();
        expense.setUser(user);
        Department department = departmentService.getById(user.getDepartment().getId());
        expense.setManager_id(department.getUser().getId());
        return expenseMapper.expenseToExpenseDto(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseDto> getAllExpenseByUserId(Long id) {
        List<ExpenseDto> result= new ArrayList<>();
        List<ExpenseProjection> expenseProjection = expenseRepository.findAllByUserId(id);
        expenseProjection.forEach
                (a -> result.add(new ExpenseDto(a.getFirstName(),a.getLastName(),a.getIdentityNumber(),
                        a.getSpendingStatement(),a.getExpenditureType(),a.getSpendingAmount(), a.getStatus(), a.getId(),
                        a.getUserId() , a.getConfirmation() , a.getExpenseTime(), a.getExpensePath())));
        return result;
    }

    @Override
    public ExpenseDto approveExpense(Long id) {
        Expense expense = expenseRepository.findById(id).get();
        expense.setConfirmation(ConfirmationStatus.CONFIRMED);
        expense.setStatus(false);
        expenseRepository.save(expense);
        return expenseMapper.expenseToExpenseDto(expense);
    }

    @Override
    public ExpenseDto rejectExpense(Long id) {
        Expense expense = expenseRepository.findById(id).get();
        expense.setStatus(false);
        expense.setConfirmation(ConfirmationStatus.REJECTED);
        expenseRepository.save(expense);
        return expenseMapper.expenseToExpenseDto(expense);
    }

    @Override
    public List<ExpenseDto> getPendingExpensesByManagerId(Long managerId){
        List<ExpenseDto> result = new ArrayList<>();
        List<ExpenseProjection> expenseProjections = expenseRepository.findAllPendingExpensesByManagerId(managerId);
        expenseProjections.forEach
                (a -> result.add(new ExpenseDto(a.getFirstName(),a.getLastName(),a.getIdentityNumber(),
                        a.getSpendingStatement(),a.getExpenditureType(),a.getSpendingAmount(), a.getStatus(),
                        a.getId(),a.getUserId(),a.getConfirmation(),a.getExpenseTime(),a.getExpensePath())));
        return result;
    }
}

