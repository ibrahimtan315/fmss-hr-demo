package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Expense;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensePaginationRepository extends PagingAndSortingRepository<Expense,Long> {

}
