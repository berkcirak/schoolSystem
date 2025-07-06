package com.workfolder.work.controller;

import com.workfolder.work.entity.Student;
import com.workfolder.work.model.StudentDTO;
import com.workfolder.work.service.JWTService;
import com.workfolder.work.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;
    private JWTService jwtService;
    public StudentController(StudentService studentService, JWTService jwtService){
        this.studentService=studentService;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public String addStudent(@RequestBody Student student){
        studentService.createStudent(student);
        return jwtService.generateToken(student.getUsername());
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody Student student){
        return studentService.verify(student);
    }
    @GetMapping("/{studentId}")
    public StudentDTO getStudent(@PathVariable int studentId){
        return studentService.getStudentById(studentId);
    }
    @GetMapping("/list")
    public List<StudentDTO> getAllStudents(){
        return studentService.getStudents();
    }
    @GetMapping("/profile")
    public StudentDTO getStudentProfile(){
        return studentService.getStudentProfile();
    }
    @PutMapping("/update/{studentId}")
    public String updateStudent(@PathVariable int studentId, @RequestBody StudentDTO studentDTO){
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO, studentId);

        String newToken = jwtService.generateToken(updatedStudent.getUsername());

        return newToken;
    }
    @DeleteMapping("/delete/{studentId}")
    public void deleteStudent(@PathVariable int studentId){
        studentService.deleteStudent(studentId);
    }

}
