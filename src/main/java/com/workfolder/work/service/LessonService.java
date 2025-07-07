package com.workfolder.work.service;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Teacher;
import com.workfolder.work.model.LessonDTO;
import com.workfolder.work.repository.LessonRepository;
import com.workfolder.work.repository.TeacherRepository;
import com.workfolder.work.response.LessonResponse;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private LessonRepository lessonRepository;
    private TeacherService teacherService;
    public LessonService(LessonRepository lessonRepository, TeacherService teacherService){
        this.lessonRepository=lessonRepository;
        this.teacherService=teacherService;
    }

    public LessonDTO createLesson(Lesson lesson){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        lesson.setTeacher(teacher);
        Lesson theLesson=lessonRepository.save(lesson);
        return new LessonDTO(theLesson);
    }
    public List<LessonDTO> getLessons(){
        List<Lesson> lessonList = lessonRepository.findAll();
        return lessonList.stream()
                .map(LessonDTO::new)
                .collect(Collectors.toList());
    }
    public LessonDTO getLessonById(int lessonId){
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson not found by id: "+lessonId));
        return new LessonDTO(lesson);
    }
    public Lesson getLessonByIdForRequestService(int lessonId){
        return lessonRepository.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson not found"));
    }
    public List<LessonResponse> getLessonsByTeacher(int teacherId){
        List<Lesson> lessonList = lessonRepository.findAllByTeacherId(teacherId);
        return lessonList.stream()
                .map(LessonResponse::new)
                .collect(Collectors.toList());
    }
    public LessonDTO updateLesson(int lessonId, LessonDTO lessonDTO){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson not found by id: "+lessonId));
        if (teacher.getId() != lesson.getTeacher().getId()){
            throw new RuntimeException("You are not authorized for update this lesson");
        }
        if (lessonDTO.getName() != null){
            lesson.setName(lessonDTO.getName());
        }
        lessonRepository.save(lesson);
        return new LessonDTO(lesson);
    }
    public void deleteLesson(int lessonId){
        Teacher teacher = teacherService.getAuthenticatedTeacher();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson not found by id: "+lessonId));
        if (teacher.getId() != lesson.getTeacher().getId()){
            throw new RuntimeException("You are not authorized for delete this lesson");
        }
        lessonRepository.deleteById(lessonId);
    }





}
