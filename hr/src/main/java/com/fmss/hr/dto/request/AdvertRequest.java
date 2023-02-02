package com.fmss.hr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AdvertRequest {

    @NotBlank(message = "{backend.constraints.title.NotBlank.message}")
    @Size(min = 5, max = 32, message = "{backend.constraints.title.Size.message}")
    private String title;

    @NotBlank(message = "{backend.constraints.description.NotBlank.message}")
    @Size(min = 10, max = 400, message = "{backend.constraints.description.Size.message}")
    private String description;

    @NotBlank(message = "{backend.constraints.jobPosition.NotBlank.message}")
    @Size(min = 2, max = 20, message = "{backend.constraints.jobPosition.Size.message}")
    private String jobPosition;

    @NotBlank(message = "{backend.constraints.department.NotBlank.message}")
    @Size(min = 2, max = 32, message = "{backend.constraints.department.Size.message}")
    private String department;

    @NotBlank(message = "{backend.constraints.mannerOfWork.NotBlank.message}")
    @Size(min = 2, max = 32, message = "{backend.constraints.mannerOfWork.Size.message}")
    private String mannerOfWork;

    private boolean status;
}
