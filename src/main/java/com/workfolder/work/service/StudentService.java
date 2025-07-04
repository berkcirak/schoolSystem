package com.workfolder.work.service;

import com.workfolder.work.entity.Student;
import com.workfolder.work.model.StudentDTO;
import com.workfolder.work.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public StudentDTO createStudent(Student student){
        studentRepository.save(student);
        return new StudentDTO(student);
    }
    public List<StudentDTO> getStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }
    public StudentDTO getStudentById(int studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("Student not found by id: "+studentId));
        return new StudentDTO(student);
    }






}
