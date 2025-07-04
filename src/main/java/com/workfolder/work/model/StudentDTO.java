package com.workfolder.work.model;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Student;

import java.util.List;

public class StudentDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String country;
    private List<Lesson> lessonList;

    public StudentDTO(Student student){
        this.username=student.getUsername();
        this.age=student.getAge();
        this.firstName=student.getFirstName();
        this.lastName=student.getLastName();
        this.password=student.getPassword();
        this.country=student.getCountry();
        this.lessonList=student.getLessonList();
    }

}
