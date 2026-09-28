package com.example.crudSpringBoot.repository;


import com.example.crudSpringBoot.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentRepository {

    public Student saveStudent(Student studentReq) {
        //save to DB
        System.out.println("inside stu repo");
        System.out.println("exiting stu repo");

        Student s = new Student();
        s.setName("ojo");
        s.setAge(23);
        s.setEmail("ojo@gmail.com");
        s.setRollNo(211);
        s.setSubject("hindi");
        return s;
    }
}
