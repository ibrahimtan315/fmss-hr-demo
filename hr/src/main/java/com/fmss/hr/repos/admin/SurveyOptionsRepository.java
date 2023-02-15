package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Survey;
import com.fmss.hr.entities.SurveyOptions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyOptionsRepository extends JpaRepository<SurveyOptions,Long> {

    List<SurveyOptions> findBySurveyId (Long id);

    @Query(value = "select * from survey_options where user_id= :id AND survey_id=:surveyOptionId" , nativeQuery = true)
    Long findUserVote(Long id, Long surveyOptionId);

    @Query(value = "select user_id from survey_options where survey_id=:surveyId" , nativeQuery = true)
    List<Long> findOptionsOfSurvey (Long surveyId);
    @Query(value = "select * from survey_options where survey_id =:surveyId order by id", nativeQuery = true)
    List<SurveyOptions> findAllBySurveyId (Long surveyId);
    @Query(value= "select * from survey_options", nativeQuery = true)
    List<SurveyOptions> findAll();
    @Query(value = "select * from survey_options where id = :surveyOptionsId", nativeQuery = true)
    SurveyOptions findBySurveyOptionsId(Long surveyOptionsId);
}
