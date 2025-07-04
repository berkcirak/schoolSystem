package com.workfolder.work.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String age;
    private String country;
    @OneToMany
    private List<Lesson> lessonList;





}
