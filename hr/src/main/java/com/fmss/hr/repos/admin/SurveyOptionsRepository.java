package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.SurveyOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyOptionsRepository extends JpaRepository<SurveyOptions,Long> {

    List<SurveyOptions> findBySurveyId (Long id);

}
