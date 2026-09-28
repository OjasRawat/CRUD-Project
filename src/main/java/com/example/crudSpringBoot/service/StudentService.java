package com.example.crudSpringBoot.service;

import com.example.crudSpringBoot.entity.Student;
import com.example.crudSpringBoot.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//Controller - Service - Repository, Entity

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq) {
        //business logic
        // pass to repository
        System.out.println("inside stu service");
        Student studentResp = studentRepository.saveStudent(studentReq);
        System.out.println("exiting stu service");
        return studentResp;
    }

}
