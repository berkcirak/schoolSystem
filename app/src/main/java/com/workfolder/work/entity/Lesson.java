package com.workfolder.work.entity;

import com.workfolder.work.model.LessonDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany(mappedBy = "lessonList")
    private List<Student> studentList;

    public Lesson(){}
    public Lesson(LessonDTO lessonDTO){}

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
