package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "taskTitle")
    private String taskTitle;

    @Column(name = "taskDescription")
    private String taskDescription;

    @Column(name = "stashLinks")
    private String stashLinks;

    @OneToMany(mappedBy = "task")
    @JsonBackReference
    private List<SheetDetails> sheetDetails;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonBackReference
    private User creator;

    @Column(name = "isCompleted")
    private Boolean isCompleted;
}
