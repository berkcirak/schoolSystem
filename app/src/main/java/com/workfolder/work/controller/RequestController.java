package com.workfolder.work.controller;

import com.workfolder.work.model.RequestDTO;
import com.workfolder.work.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/request")
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService=requestService;
    }
    @PostMapping("/send/{lessonId}")
    public RequestDTO sendRequest(@PathVariable int lessonId){
        return requestService.createRequest(lessonId);
    }
    @GetMapping("/teacher/list")
    public List<RequestDTO> getRequestsForTeacher(){
        return requestService.getPendingRequestsForTeacher();
    }
    @PutMapping("/accept/{requestId}")
    public RequestDTO acceptRequest(@PathVariable int requestId){
        return requestService.acceptRequest(requestId);
    }
    @PutMapping("/reject/{requestId}")
    public RequestDTO rejectRequest(@PathVariable int requestId){
        return requestService.rejectRequest(requestId);
    }
    @GetMapping("/student/list")
    public List<RequestDTO> getRequestsForStudent(){
        return requestService.getRequestsForStudent();
    }




}
