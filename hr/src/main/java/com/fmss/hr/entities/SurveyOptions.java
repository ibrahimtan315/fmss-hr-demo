package com.fmss.hr.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "surveyOptions")
@Data
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

    @OneToMany(mappedBy = "surveyOptions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyOption_id", nullable = false, referencedColumnName = "id")
    private Survey survey;



}
