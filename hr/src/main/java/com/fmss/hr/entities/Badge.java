package com.fmss.hr.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "badges")
@Data
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
