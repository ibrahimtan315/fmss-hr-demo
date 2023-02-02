package com.fmss.hr.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="adverts")
@Data
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{backend.constraints.username.NotBlank.message}")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "jobPosition", nullable = false)
    private String jobPosition;

    @Column(name = "mannerOfWork", nullable = false)
    private String mannerOfWork;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "status")
    private Boolean status;
}