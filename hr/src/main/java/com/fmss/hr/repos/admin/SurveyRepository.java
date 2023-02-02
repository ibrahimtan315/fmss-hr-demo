package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository <Survey, Long> {
}
