package com.eteach.eteach.service;

import com.eteach.eteach.dao.LessonDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LessonService {

    private LessonDAO lessonDAO;

    @Autowired
    public LessonService(@Qualifier("lessondaoimp") LessonDAO lessonDAO){
        this.lessonDAO = lessonDAO;
    }

    public Lesson saveLesson(Lesson lesson){
        return this.lessonDAO.save(lesson);
    }

    public Lesson updateLesson(Lesson oldLesson, Lesson newLesson){
        return this.lessonDAO.save(newLesson);
    }

    public Lesson getLesson(Long id){
        Lesson lesson =  this.lessonDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id));
        return lesson;
    }

    public List<Lesson> getAllLessons(){
        return this.lessonDAO.findAll();
    }

    public void deleteLesson(Lesson lesson){
        this.lessonDAO.delete(lesson);
    }
}
