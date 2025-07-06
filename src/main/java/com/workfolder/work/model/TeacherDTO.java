package com.workfolder.work.model;

import com.workfolder.work.entity.Teacher;
import com.workfolder.work.entity.User;

public class TeacherDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String country;
    private User.UserType userType;


    public TeacherDTO(Teacher teacher){
        this.username=teacher.getUsername();
        this.password= teacher.getPassword();
        this.firstName=teacher.getFirstName();
        this.lastName=teacher.getLastName();
        this.age=teacher.getAge();
        this.country=teacher.getCountry();
        this.userType=teacher.getUserType();
    }

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

    public User.UserType getUserType() {
        return userType;
    }

    public void setUserType(User.UserType userType) {
        this.userType = userType;
    }
}

