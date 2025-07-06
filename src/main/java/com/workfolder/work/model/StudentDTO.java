package com.workfolder.work.model;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Student;
import com.workfolder.work.entity.User;

import java.util.List;

public class StudentDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String country;
    private List<Lesson> lessonList;
    private User.UserType userType;

    public StudentDTO(Student student){
        this.username=student.getUsername();
        this.age=student.getAge();
        this.firstName=student.getFirstName();
        this.lastName=student.getLastName();
        this.password=student.getPassword();
        this.country=student.getCountry();
        this.lessonList=student.getLessonList();
        this.userType=student.getUserType();
    }

    public User.UserType getUserType() {
        return userType;
    }

    public void setUserType(User.UserType userType) {
        this.userType = userType;
    }

    public StudentDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }
}
