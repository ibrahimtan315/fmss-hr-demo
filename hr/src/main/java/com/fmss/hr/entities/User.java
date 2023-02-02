package com.fmss.hr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Transactional
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "identityNumber")
    private String identityNumber;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "startingDateOfEmployment")
    private LocalDate startingDateOfEmployment;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch =FetchType.LAZY,optional = false)
    @JsonIgnore
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;

    @Column(name = "level")
    private String level;

    @Column(name = "salary")
    private BigDecimal salary;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "departmentId", nullable = true)
    private Department department;

    @Column(name = "address")
    private String address;

    @Column(name = "title")
    private String title;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name= "lastLoginDate")
    private LocalDate lastLoginDate;

    @OneToMany(mappedBy = "creator")
    @JsonManagedReference
    @ToString.Exclude
    private List<Task> tasks;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<TimeSheet> timeSheet;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<EventUser> eventUserList;

    @Column(name = "photo_path")
    private String photoPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyOption_id", nullable = false, referencedColumnName = "id")
    private SurveyOptions surveyOptions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getRole().getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
