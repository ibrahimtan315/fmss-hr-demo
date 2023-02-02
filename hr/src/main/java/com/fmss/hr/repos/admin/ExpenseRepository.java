package com.fmss.hr.repos.admin;

import com.fmss.hr.dto.projection.ExpenseProjection;
import com.fmss.hr.dto.projection.ExpenseUserProjection;
import com.fmss.hr.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {


        @Query(value = "SELECT u.first_name as firstName, u.last_name as lastName, u.identity_number as identityNumber, " +
                "ex.expenditure_type as expenditureType," +
                " ex.spending_amount as spendingAmount, ex.spending_statement as " +
                "spendingStatement, ex.status as status, ex.id as id FROM expense ex " +
                "INNER JOIN users u ON ex.user_id = u.id WHERE ex.status=true and ex.confirmation =false"
                , nativeQuery = true)
        List<ExpenseProjection> findAllExpenseUsers();

        List<ExpenseProjection> findAllByUserId(Long id);
        Optional<Expense> findById(Long user_id);


        @Query(value = "SELECT u.first_name as firstName, u.last_name as lastName, u.identity_number as identityNumber," +
                " ex.expenditure_type as expenditureType, ex.spending_amount as spendingAmount, " +
                "ex.spending_statement as spendingStatement, ex.status as status , ex.id as id FROM expense ex " +
                "INNER JOIN users u ON ex.user_id = u.id WHERE ex.status=true"
                , nativeQuery = true)
        Page<ExpenseProjection> pagination(Pageable pageable);

        @Query(value = "SELECT ex.expenditure_type as expenditureType, ex.spending_amount as spendingAmount," +
                " ex.spending_statement as spendingStatement, ex.status as status, ex.id as id " +
                "FROM Expense ex WHERE ex.status=true and ex.user_id= :id",
                nativeQuery = true)
        Page<ExpenseUserProjection> userPageable(Long id, Pageable pageable);

        @Query(value = "SELECT u.first_name as firstName, u.last_name as lastName, u.identity_number as identityNumber ," +
                " ex.confirmation as confirmation , ex.spending_statement as spendingStatement , " +
                "ex.spending_amount as spendingAmount , ex.status as status , ex.id as id ,ex.expense_time as expenseTime," +
                "ex.user_id as userId , ex.expenditure_type as expenditureType FROM Expense ex " +
                "INNER JOIN users u ON ex.user_id=u.id " +
                "where ex.manager_id=:managerId AND ex.confirmation='PENDING' " ,
                nativeQuery = true)
        List<ExpenseProjection> findAllPendingExpensesByManagerId(@Param("managerId") Long managerId);

}
