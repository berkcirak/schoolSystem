package com.workfolder.work.model;

import com.workfolder.work.entity.Request;

import java.time.LocalDateTime;

public class RequestDTO {

    private int id;
    private String studentName;
    private String lessonName;
    private String teacherName;
    private String status;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;

    public RequestDTO(){}
    public RequestDTO(Request request){
        this.id=request.getId();
        this.studentName=request.getStudent().getUsername();
        this.lessonName=request.getLesson().getName();
        this.teacherName=request.getTeacher().getUsername();
        this.status=request.getRequestStatus().name();
        this.requestDate=request.getRequestDate();
        this.responseDate=request.getResponseDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }
}
