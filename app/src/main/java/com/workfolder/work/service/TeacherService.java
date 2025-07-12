package com.workfolder.work.service;

import com.workfolder.work.entity.Student;
import com.workfolder.work.entity.Teacher;
import com.workfolder.work.entity.User;
import com.workfolder.work.entity.UserPrincipal;
import com.workfolder.work.model.StudentDTO;
import com.workfolder.work.model.TeacherDTO;
import com.workfolder.work.repository.TeacherRepository;
import jakarta.persistence.PersistenceUnit;
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
public class TeacherService {

    private TeacherRepository teacherRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    public TeacherService(TeacherRepository teacherRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JWTService jwtService){
        this.teacherRepository=teacherRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }
    public Teacher createTeacher(Teacher teacher){
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacher.setUserType(User.UserType.TEACHER);
        return teacherRepository.save(teacher);
    }


    public String verify(Teacher teacher){
        Teacher theTeacher = teacherRepository.findByUsername(teacher.getUsername());
        if (theTeacher == null){
            throw new RuntimeException("Teacher not found by username: "+ teacher.getUsername());
        }
        if (theTeacher.getUserType() != User.UserType.TEACHER){
            throw new RuntimeException("You are not a teacher role");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(teacher.getUsername(), teacher.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(teacher.getUsername());
        }
        return "Login failed";
    }


    public List<TeacherDTO> getTeachers() {
        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherList.stream()
                .map(TeacherDTO::new)
                .collect(Collectors.toList());
    }
    public TeacherDTO updateTeacher(int teacherId, TeacherDTO teacherDTO){
        Teacher teacher = getAuthenticatedTeacher();
        if (teacher.getId() != teacherId){
            throw new RuntimeException("You are not authorized for update this teacher");
        }
        if (teacherDTO.getUsername() != null){
            teacher.setUsername(teacherDTO.getUsername());
        }
        if (teacherDTO.getPassword() != null){
            teacher.setPassword(bCryptPasswordEncoder.encode(teacherDTO.getPassword()));
        }
        if (teacherDTO.getAge() != null){
            teacher.setAge(teacherDTO.getAge());
        }
        if (teacherDTO.getCountry() != null){
            teacher.setCountry(teacher.getCountry());
        }
        if (teacherDTO.getFirstName() != null){
            teacher.setFirstName(teacherDTO.getFirstName());
        }
        if (teacherDTO.getLastName() != null){
            teacher.setLastName(teacherDTO.getLastName());
        }
        if (teacherDTO.getEmail() != null){
            teacher.setEmail(teacher.getEmail());
        }
        teacherRepository.save(teacher);
        return new TeacherDTO(teacher);
    }
    public void deleteTeacher(int teacherId){
        Teacher teacher = getAuthenticatedTeacher();
        if (teacher.getId() != teacherId){
            throw new RuntimeException("You are not authorized for delete this user");
        }
        teacherRepository.deleteById(teacherId);
    }
    public Teacher getAuthenticatedTeacher(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails){
            username = ((UserPrincipal) principal).getUsername();
        }else {
            username = principal.toString();
        }
        return teacherRepository.findByUsername(username);

    }

    public TeacherDTO getTeacherById(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()->new RuntimeException("Teacher not found by id: "+teacherId));
        return new TeacherDTO(teacher);
    }

    public TeacherDTO getTeacherProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal){
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Teacher theTeacher = teacherRepository.findByUsername(username);
            return new TeacherDTO(theTeacher);
        }
        throw new RuntimeException("Teacher not found");
    }



}
