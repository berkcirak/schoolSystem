package com.workfolder.work.model;

import com.workfolder.work.entity.Lesson;

public class LessonDTO {

    private int id;
    private String name;
    private String teacher;

    public LessonDTO(Lesson lesson){
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.teacher = lesson.getTeacher().getUsername();
    }
    public LessonDTO(){}

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
