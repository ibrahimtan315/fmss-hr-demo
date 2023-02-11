package com.fmss.hr.entities;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SurveyOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String options;
    private int counter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId", nullable = false, referencedColumnName = "id")
    private Survey survey;



}
