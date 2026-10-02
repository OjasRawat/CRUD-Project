package com.example.crudSpringBoot.service;

import com.example.crudSpringBoot.dto.CreateStudentRequestDTO;
import com.example.crudSpringBoot.dto.CreateStudentResponseDTO;
import com.example.crudSpringBoot.dto.UpdateStudentRequestDto;
import com.example.crudSpringBoot.dto.UpdateStudentResponseDto;
import com.example.crudSpringBoot.entity.Student;
import com.example.crudSpringBoot.exception.DuplicateResourceException;
import com.example.crudSpringBoot.exception.ResourceNotFoundException;
import com.example.crudSpringBoot.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//Controller - Service - Repository, Entity
//business logic
// pass to repository
@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO studentRequestDTO) {
        Student student = mapToCreateEntity(studentRequestDTO);

        if (emailExists(student)) {
            throw new DuplicateResourceException("STUDENT WITH EMAIL "+student.getEmail()+" ALREADY EXISTS");
        }

        Student studentResp = studentRepository.save(student);
        return mapToCreateDTO(studentResp);
    }

    public CreateStudentResponseDTO getStudent(Long id) {
        Student studentResp = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT WITH ID "+id+" NOT FOUND."));
        return mapToCreateDTO(studentResp);
    }

    public List<CreateStudentResponseDTO> getAllStudent() {
        List<Student> lst = studentRepository.findByDeletedIsFalse();
        return lst.stream().map(this::mapToCreateDTO).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {
        Student existingStudent = studentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT WITH ID "+id+" NOT FOUND."));

        existingStudent.setName(studentReq.getName());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setAge(studentReq.getAge());

        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());
        Student savedStudent = studentRepository.save(existingStudent);

        return mapToUpdateDto(savedStudent);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT WITH ID "+id+" NOT FOUND."));

        studentRepository.delete(student);
    }

    public void deleteStudentSoftly(Long id) {
        Student student = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("STUDENT WITH ID "+id+" NOT FOUND.")
                );

        student.setDeleted(true);
        studentRepository.save(student);
    }

    private Student mapToCreateEntity(CreateStudentRequestDTO studentRequestDTO) {
        Student student = new Student();

        student.setName(studentRequestDTO.getName());
        student.setAge(studentRequestDTO.getAge());
        student.setEmail(studentRequestDTO.getEmail());
        student.setRollNo(studentRequestDTO.getRollNo());
        student.setSubject(studentRequestDTO.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return student;
    }

    private CreateStudentResponseDTO mapToCreateDTO(Student student) {
        CreateStudentResponseDTO studentResponseDTO = new CreateStudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setSubject(student.getSubject());

        studentResponseDTO.setMessage("STUDENT SAVED SUCCESSFULLY");
        studentResponseDTO.setCreatedAt(student.getCreatedAt());
        studentResponseDTO.setUpdatedAt(student.getUpdatedAt());

        return studentResponseDTO;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto studentResponseDTO = new UpdateStudentResponseDto();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setSubject(student.getSubject());

        studentResponseDTO.setMessage("STUDENT UPDATED SUCCESSFULLY");
        studentResponseDTO.setUpdatedAt(student.getUpdatedAt());

        return studentResponseDTO;
    }

    private boolean emailExists(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }

}
