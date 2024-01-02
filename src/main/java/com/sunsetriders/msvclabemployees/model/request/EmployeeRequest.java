package com.sunsetriders.msvclabemployees.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotEmpty
    @NotNull
    private String gender;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime hireDate;

    @NotEmpty
    @NotNull
    private String department;

    @NotEmpty
    @NotNull
    private String jobTitle;

    @NotEmpty
    @NotNull
    private String salary;
}
