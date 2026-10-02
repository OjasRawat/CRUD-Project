package com.example.crudSpringBoot.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDTO {

    //spring boot started validation
    @NotBlank(message = "Name cannot be blank/null/empty")
    @Size(min = 2, max = 50, message = "Student name must be within 2-50 length")
    private String name;
    @Min(value = 18)
    private int age;
    @Email
    private String email;
    @NotNull
    private int rollNo;
    @NotBlank
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
