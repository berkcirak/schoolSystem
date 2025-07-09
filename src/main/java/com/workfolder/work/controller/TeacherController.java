package com.workfolder.work.controller;

import com.workfolder.work.entity.Teacher;
import com.workfolder.work.model.TeacherDTO;
import com.workfolder.work.service.JWTService;
import com.workfolder.work.service.TeacherService;
import jakarta.persistence.PersistenceUnit;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private TeacherService teacherService;
    private JWTService jwtService;

    public TeacherController(TeacherService teacherService, JWTService jwtService){
        this.teacherService=teacherService;
        this.jwtService=jwtService;
    }
    @PostMapping("/register")
    public String createTeacher(@RequestBody Teacher teacher){
        teacherService.createTeacher(teacher);
        return jwtService.generateToken(teacher.getUsername());
    }
    @PostMapping("/login")
    public String loginTeacher(@RequestBody Teacher teacher){
        return teacherService.verify(teacher);
    }
    @GetMapping("/list")
    public List<TeacherDTO> getTeachers(){
        return teacherService.getTeachers();
    }
    @GetMapping("/{teacherId}")
    public TeacherDTO getTeacherById(@PathVariable int teacherId){
        return teacherService.getTeacherById(teacherId);
    }

    @GetMapping("/profile")
    public TeacherDTO getTeacherProfile(){
        return teacherService.getTeacherProfile();
    }
    @PutMapping("/update/{teacherId}")
    public String updateTeacher(@PathVariable int teacherId, @RequestBody TeacherDTO teacherDTO){
        teacherService.updateTeacher(teacherId, teacherDTO);
        return jwtService.generateToken(teacherDTO.getUsername());
    }
    @DeleteMapping("/delete/{teacherId}")
    public void deleteTeacher(@PathVariable int teacherId){
        teacherService.deleteTeacher(teacherId);
    }
}
