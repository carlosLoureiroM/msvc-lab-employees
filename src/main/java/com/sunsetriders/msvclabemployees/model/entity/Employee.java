package com.sunsetriders.msvclabemployees.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String gender;

    private String email;

    private String phoneNumber;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime hireDate;

    private String department;

    private String jobTitle;

    private String salary;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
}
