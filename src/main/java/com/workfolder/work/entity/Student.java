package com.workfolder.work.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "tbl_students")
@Entity
public class Student extends User{

    private String firstName;
    private String lastName;
    private Integer age;
    private String country;
    @ManyToMany
    @JoinTable(name = "tbl_students_lesson_list",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<Lesson> lessonList;

    public Student(){}

    public Student(String firstName, String lastName, int age, String country, List<Lesson> lessonList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.country = country;
        this.lessonList = lessonList;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
