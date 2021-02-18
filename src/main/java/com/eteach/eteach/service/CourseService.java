package com.eteach.eteach.service;

import com.eteach.eteach.dao.CourseDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseDAO courseDAO;

    @Autowired
    public CourseService(@Qualifier("coursedaoimp") CourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    public Course createCourse(Course course){
        return this.courseDAO.save(course);
    }

    public Course updateCourse(Course oldCourse, Course newCourse){
        return this.courseDAO.save(newCourse);
    }

    public Course getCourse(Long id){
        Course course =  this.courseDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        return course;
    }

    public List<Course> getAllCourses(){
        return this.courseDAO.findAll();
    }

    public void deleteCourse(Course course){
        this.courseDAO.delete(course);
    }
}
