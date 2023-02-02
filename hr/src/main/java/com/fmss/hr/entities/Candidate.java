package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "candidates")
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name="registerDate")
    private LocalDateTime registerDate;

    @Column(name = "cvPath")
    private String cvPath;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "referenceId", nullable = true)
    @OnDelete(action =OnDeleteAction.NO_ACTION)
    private User user;

    @ManyToOne
    @JoinColumn(name="advertId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Advert advert;


    @OneToMany(mappedBy = "candidate")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Comment> commentList;


}
