package com.fmss.hr.repos.user;

import com.fmss.hr.entities.SheetDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheetDetailsRepository extends JpaRepository<SheetDetails, Long> {

    List<SheetDetails> findAllByTimeSheetId(Long sheetId);
}
