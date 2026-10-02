package com.example.crudSpringBoot.service;

import com.example.crudSpringBoot.dto.CreateStudentRequestDTO;
import com.example.crudSpringBoot.dto.CreateStudentResponseDTO;
import com.example.crudSpringBoot.dto.UpdateStudentRequestDto;
import com.example.crudSpringBoot.dto.UpdateStudentResponseDto;
import com.example.crudSpringBoot.entity.Student;
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

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        Student studentResp = studentRepository.save(student);
        return mapToCreateDTO(studentResp);
    }

    public CreateStudentResponseDTO getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResp.isPresent()) {
            return mapToCreateDTO(studentResp.get());
        }
        return null;
    }

    public List<CreateStudentResponseDTO> getAllStudent() {
        List<Student> lst = studentRepository.findByDeletedIsFalse();
        return lst.stream().map(this::mapToCreateDTO).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (existingStudent.isEmpty()) return null;

        Student studentToSave = existingStudent.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setAge(studentReq.getAge());

        studentToSave.setDeleted(false);
        studentToSave.setUpdatedAt(LocalDateTime.now());
        Student savedStudent = studentRepository.save(studentToSave);

        return mapToUpdateDto(savedStudent);
    }

    public Boolean deleteStudent(Long id) {
        Boolean isStudent = studentRepository.existsById(id);
        if (!isStudent) return false;

        studentRepository.deleteById(id);
        return true;
    }

    public Boolean deleteStudentSoftly(Long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (existingStudent.isEmpty()) return false;

        Student studentToSave = existingStudent.get();

        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);
        return true;
    }

    private Student mapToCreateEntity(CreateStudentRequestDTO studentRequestDTO) {
        Student student = new Student();

        student.setName(studentRequestDTO.getName());
        student.setAge(studentRequestDTO.getAge());
        student.setEmail(studentRequestDTO.getEmail());
        student.setRollNo(studentRequestDTO.getRollNo());
        student.setSubject(studentRequestDTO.getSubject());

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

}
