package com.workfolder.work.service;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Student;
import com.workfolder.work.entity.User;
import com.workfolder.work.model.StudentDTO;
import com.workfolder.work.repository.LessonRepository;
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
    private LessonRepository lessonRepository;
    private KafkaProducerService kafkaProducerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public StudentService(StudentRepository studentRepository, KafkaProducerService kafkaProducerService, LessonRepository lessonRepository, JWTService jwtService,AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.studentRepository=studentRepository;
        this.jwtService=jwtService;
        this.lessonRepository=lessonRepository;
        this.kafkaProducerService=kafkaProducerService;
        this.authenticationManager=authenticationManager;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public Student createStudent(Student student){
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        student.setUserType(User.UserType.STUDENT);
        Student savedStudent = studentRepository.save(student);
        kafkaProducerService.sendUserRegisteredEvent(savedStudent.getEmail(), savedStudent.getFirstName());
        return savedStudent;
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

    public StudentDTO updateStudent(StudentDTO studentDTO, int studentId){
        Student theStudent = getAuthenticatedStudent();
        if (theStudent.getId() != studentId){
            throw new RuntimeException("You are not authorized to update this user");
        }
        if (studentDTO.getUsername() != null){
            theStudent.setUsername(studentDTO.getUsername());
        }
        if (studentDTO.getAge() != null){
            theStudent.setAge(studentDTO.getAge());
        }
        if (studentDTO.getCountry() != null){
            theStudent.setCountry(studentDTO.getCountry());
        }
        if (studentDTO.getFirstName() != null){
            theStudent.setFirstName(studentDTO.getFirstName());
        }
        if (studentDTO.getLastName() != null){
            theStudent.setLastName(studentDTO.getLastName());
        }
        if (studentDTO.getEmail() != null){
            theStudent.setEmail(studentDTO.getEmail());
        }
        if (studentDTO.getLessonList() != null){
            List<Lesson> lessons = studentDTO.getLessonList()
                    .stream().map(lessonDTO -> lessonRepository.findById(lessonDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Lesson not found by id: "+ lessonDTO.getId())))
                    .collect(Collectors.toList());
            theStudent.setLessonList(lessons);
        }
        if (studentDTO.getPassword() != null){
            theStudent.setPassword(bCryptPasswordEncoder.encode(studentDTO.getPassword()));
        }
        studentRepository.save(theStudent);
        return new StudentDTO(theStudent);

    }
    public void deleteStudent(int studentId){
        Student student = getAuthenticatedStudent();
        if (student.getId() != studentId){
            throw new RuntimeException("You are not authorized for delete this user");
        }
        studentRepository.deleteById(studentId);
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
        Student theStudent = studentRepository.findByUsername(student.getUsername());
        if (theStudent == null){
            throw new RuntimeException("Student not found by id: "+ theStudent.getUsername());
        }
        if (theStudent.getUserType() != User.UserType.STUDENT){
            throw new RuntimeException("You are not have student role");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(student.getUsername());
        }
        return "Login failed";
    }

    public StudentDTO getStudentProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Student theStudent = studentRepository.findByUsername(username);
            return new StudentDTO(theStudent);
        }
        throw new RuntimeException("Student not found");
    }



}
