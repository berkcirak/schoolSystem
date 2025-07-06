package com.workfolder.work.response;

import com.workfolder.work.entity.Lesson;

public class LessonResponse {

    private String name;
    private String teacherName;

    public LessonResponse(Lesson lesson){
        this.name=lesson.getName();
        this.teacherName=lesson.getTeacher().getFirstName()+" "+lesson.getTeacher().getLastName();
    }
    public LessonResponse(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
