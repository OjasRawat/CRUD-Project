package com.example.crudSpringBoot.controller;

import com.example.crudSpringBoot.dto.CreateStudentRequestDTO;
import com.example.crudSpringBoot.dto.CreateStudentResponseDTO;
import com.example.crudSpringBoot.dto.UpdateStudentRequestDto;
import com.example.crudSpringBoot.dto.UpdateStudentResponseDto;
import com.example.crudSpringBoot.entity.Student;
import com.example.crudSpringBoot.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students") // common end point
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDTO> createStudent(
            @Valid @RequestBody CreateStudentRequestDTO studentRequestDTO) {
        CreateStudentResponseDTO createdStudent = studentService.createStudent(studentRequestDTO);
        return ResponseEntity
                .status(201)
                .body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDTO> getStudent(@PathVariable Long id) {
        CreateStudentResponseDTO studentResp = studentService.getStudent(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> getAllStudent() {
        List<CreateStudentResponseDTO> studentResp = studentService.getAllStudent();
        return ResponseEntity.ok(studentResp);
    }

    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id,
                                                 @RequestBody UpdateStudentRequestDto studentReq) {
        UpdateStudentResponseDto studentResp = studentService.updateStudent(id, studentReq);
        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);

       return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
        studentService.deleteStudentSoftly(id);
        return ResponseEntity.noContent().build();
    }


}
