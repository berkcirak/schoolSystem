package com.workfolder.work.controller;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.model.LessonDTO;
import com.workfolder.work.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;
    public LessonController(LessonService lessonService){
        this.lessonService=lessonService;
    }
    @PostMapping("/add")
    public Lesson addLesson(@RequestBody Lesson lesson){
        return lessonService.createLesson(lesson);
    }
    @GetMapping("/list")
    public List<LessonDTO> getLessons(){
        return lessonService.getLessons();
    }
    @GetMapping("/{lessonId}")
    public LessonDTO getLesson(@PathVariable int lessonId){
        return lessonService.getLessonById(lessonId);
    }
    @PutMapping("/update/{lessonId}")
    public LessonDTO updateLesson(@PathVariable int lessonId, @RequestBody LessonDTO lessonDTO){
        return lessonService.updateLesson(lessonId, lessonDTO);
    }
    @DeleteMapping("/delete/{lessonId}")
    public void deleteLesson(@PathVariable int lessonId){
        lessonService.deleteLesson(lessonId);
    }


}
