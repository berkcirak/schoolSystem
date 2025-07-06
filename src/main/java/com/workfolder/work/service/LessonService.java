package com.workfolder.work.service;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.entity.Teacher;
import com.workfolder.work.model.LessonDTO;
import com.workfolder.work.repository.LessonRepository;
import com.workfolder.work.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private LessonRepository lessonRepository;
    private TeacherRepository teacherRepository;
    public LessonService(LessonRepository lessonRepository, TeacherRepository teacherRepository){
        this.lessonRepository=lessonRepository;
        this.teacherRepository=teacherRepository;
    }

    public Lesson createLesson(Lesson lesson){
        return lessonRepository.save(lesson);
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
    public LessonDTO updateLesson(int lessonId, LessonDTO lessonDTO){
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson not found by id: "+lessonId));
        if (lesson.getId() != lessonId){
            throw new RuntimeException("Wrong lesson id");
        }
        if (lessonDTO.getName() != null){
            lesson.setName(lessonDTO.getName());
        }
        if (lessonDTO.getTeacher() != null){
            Teacher teacher = teacherRepository.findByUsername(lessonDTO.getTeacher());
            if (teacher == null){
                throw new RuntimeException("Teacher not found with username: "+lessonDTO.getTeacher());
            }
            lesson.setTeacher(teacher);
        }
        lessonRepository.save(lesson);
        return new LessonDTO(lesson);
    }
    public void deleteLesson(int lessonId){
        lessonRepository.deleteById(lessonId);
    }





}
