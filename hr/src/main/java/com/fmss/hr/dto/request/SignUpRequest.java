package com.fmss.hr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class SignUpRequest {

    private String firstName;

    private String lastName;

    private String identityNumber;

    private LocalDate birthday;

    private String phoneNumber;

    private LocalDate startingDateOfEmployment;

    private String level;

    private BigDecimal salary;

    private String department;

    private String address;

    private String title;

    private String city;

    private String country;

    private String postalCode;

    @NotBlank(message = "{backend.constraints.username.NotBlank.message}")
    @Size(min = 3, max = 50, message = "{backend.constraints.username.Size.message}")
    private String email;

    @NotBlank(message = "{backend.constraints.password.NotBlank.message}")
    @Size(min = 3, max = 32, message = "{backend.constraints.password.Size.message}")
    private String password;

    @NotBlank(message = "{backend.constraints.role.Size.message}")
    @Size(min = 2, max = 32, message = "{backend.constraints.role.Size.message}")
    private String role;

    private Boolean status;

    private String photoPath;

}
