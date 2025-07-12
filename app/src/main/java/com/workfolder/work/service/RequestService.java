package com.workfolder.work.service;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Request;
import com.workfolder.work.entity.Student;
import com.workfolder.work.entity.Teacher;
import com.workfolder.work.model.LessonDTO;
import com.workfolder.work.model.RequestDTO;
import com.workfolder.work.repository.RequestRepository;
import com.workfolder.work.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private RequestRepository requestRepository;
    private StudentService studentService;
    private StudentRepository studentRepository;
    private LessonService lessonService;
    private TeacherService teacherService;
    public RequestService(StudentService studentService, StudentRepository studentRepository, LessonService lessonService, RequestRepository requestRepository, TeacherService teacherService){
        this.studentService=studentService;
        this.studentRepository=studentRepository;
        this.lessonService=lessonService;
        this.requestRepository=requestRepository;
        this.teacherService=teacherService;
    }

    //create request to lesson for student
    public RequestDTO createRequest(int lessonId){
        Student student = studentService.getAuthenticatedStudent();
        Lesson lesson = lessonService.getLessonByIdForRequestService(lessonId);
        Teacher teacher = lesson.getTeacher();

        if (lesson.getStudentList() != null && lesson.getStudentList().size() >= 5){
            throw new RuntimeException("This lesson is full");
        }
        List<Request> existingRequest = requestRepository.findByStudentId(student.getId());
        boolean alreadyRequested = existingRequest.stream()
                .anyMatch(request -> request.getLesson().getId() == lessonId);
        if (alreadyRequested){
            throw new RuntimeException("You have already requested this lesson");
        }
        Request request =new Request(student, teacher, lesson);
        Request savedRequest = requestRepository.save(request);
        return new RequestDTO(savedRequest);
    }
    //return pending requests for teacher
    public List<RequestDTO> getPendingRequestsForTeacher(){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        List<Request> requestList = requestRepository.findByTeacherIdAndStatus(teacher.getId(), Request.RequestStatus.PENDING);
        return requestList.stream()
                .map(RequestDTO::new)
                .collect(Collectors.toList());
    }
    //accept from teacher
    public RequestDTO acceptRequest(int requestId){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(()->new RuntimeException("Request not found"));

        if (request.getTeacher().getId() != teacher.getId()){
            throw new RuntimeException("You are not authorized to approve this request");
        }
        if (request.getLesson().getStudentList().size() >= 5){
            throw new RuntimeException("capacity is full");
        }
        request.setRequestStatus(Request.RequestStatus.APPROVED);
        request.setResponseDate(LocalDateTime.now());
        Request updatedRequest = requestRepository.save(request);

        Student student = request.getStudent();
        List<Lesson> lessonList = student.getLessonList();
        if (lessonList == null){
            lessonList = new ArrayList<>();
        }
        lessonList.add(request.getLesson());
        student.setLessonList(lessonList);
        studentRepository.save(student);
        return new RequestDTO(updatedRequest);
    }
    //Reject request from teacher
    public RequestDTO rejectRequest(int requestId){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(()->new RuntimeException("Request not found"));
        if (request.getTeacher().getId() != teacher.getId()){
            throw new RuntimeException("You are not authorized for reject this request");
        }
        request.setRequestStatus(Request.RequestStatus.REJECTED);
        request.setResponseDate(LocalDateTime.now());
        Request theRequest = requestRepository.save(request);
        return new RequestDTO(theRequest);
    }
    public List<RequestDTO> getRequestsForStudent(){
        Student student = studentService.getAuthenticatedStudent();
        List<Request> requestList = requestRepository.findByStudentId(student.getId());
        return requestList.stream()
                .map(RequestDTO::new)
                .collect(Collectors.toList());
    }


}
