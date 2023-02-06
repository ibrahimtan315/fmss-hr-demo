package com.fmss.hr.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "surveyOptions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "option")
    private String option;
    @Column(name = "counter")
    private int counter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyOption_id", nullable = false, referencedColumnName = "id")
    private Survey survey;


}
