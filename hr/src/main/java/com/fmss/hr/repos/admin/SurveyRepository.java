package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Survey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyRepository extends JpaRepository <Survey, Long> {

    List<Survey> findAllByStatus(Boolean status, Pageable pageable);

    List<Survey> findAllByStatusAndTitleContainingIgnoreCase(boolean status, String title, Pageable pageable);
    List<Survey> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query(value = "select count(*) from survey where status= :status", nativeQuery = true)
    int surveyCountWithStatus(Boolean status);
    @Query(value = "select count(*) from survey", nativeQuery = true)
    int surveyCount();
    Survey getSurveyById(Long id);

}
