package com.workfolder.work.service;

import com.workfolder.work.entity.Student;
import com.workfolder.work.model.StudentDTO;
import com.workfolder.work.repository.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public StudentService(StudentRepository studentRepository, JWTService jwtService,AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.studentRepository=studentRepository;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public Student createStudent(Student student){
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
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

    public Student getAuthenticatedStudent(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return studentRepository.findByUsername(username);
    }

    public String verify(Student student){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(student.getUsername());
        }
        return "fail";
    }



}
