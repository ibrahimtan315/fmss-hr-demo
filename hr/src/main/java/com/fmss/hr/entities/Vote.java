package com.fmss.hr.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "survey_id")
    private Long surveyId;
    @Column(name = "surveyOption_id")
    private Long surveyOptionsId;
    @Column(name = "user_id")
    private Long userId;


}
