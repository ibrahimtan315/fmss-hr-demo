package com.fmss.hr.repos.user;

import com.fmss.hr.entities.ExpenceImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenceStorageRepository extends JpaRepository<ExpenceImageData,Long> {


    Optional<ExpenceImageData> findByName(String fileName);
    Optional<ExpenceImageData> findByExpenseId(Long expenseId);
}