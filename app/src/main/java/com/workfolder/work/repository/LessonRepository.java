package com.workfolder.work.repository;

import com.workfolder.work.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByTeacherId(int teacherId);
}
