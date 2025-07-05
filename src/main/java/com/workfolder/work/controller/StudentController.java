package com.workfolder.work.controller;

import com.workfolder.work.entity.Student;
import com.workfolder.work.service.JWTService;
import com.workfolder.work.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
