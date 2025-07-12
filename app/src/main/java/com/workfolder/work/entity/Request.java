package com.workfolder.work.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table
@Entity(name = "tbl_requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Lesson lesson;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;

    public enum RequestStatus{
        PENDING, APPROVED, REJECTED
    }
    public Request(){}
    public Request(Student student, Teacher teacher, Lesson lesson){
        this.student=student;
        this.teacher=teacher;
        this.lesson=lesson;
        this.status=RequestStatus.PENDING;
        this.requestDate=LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public RequestStatus getRequestStatus() {
        return status;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.status = requestStatus;
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
