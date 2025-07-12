package com.workfolder.work.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "tbl_teachers")
@Entity
public class Teacher extends User{

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String country;
    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessonList;

    public Teacher(String firstName, String lastName, String email, Integer age, String country, List<Lesson> lessonList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.age = age;
        this.country = country;
        this.lessonList = lessonList;
    }
    public Teacher(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
