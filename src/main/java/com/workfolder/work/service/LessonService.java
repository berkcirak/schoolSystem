package com.workfolder.work.service;

import com.workfolder.work.entity.Lesson;
import com.workfolder.work.repository.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    private LessonRepository lessonRepository;
    public LessonService(LessonRepository lessonRepository){
        this.lessonRepository=lessonRepository;
    }






}
