package com.eteach.eteach.service;

import com.eteach.eteach.dao.CourseDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private CourseDAO courseDAO;

    @Autowired
    public CourseService(CourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    public Course saveCourse(Course course){
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

    public List<Course> getCourses(Integer pageNo, Integer pageSize, String sortBy){
        System.out.println("course service get courses 1");
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        System.out.println("paging : " + paging);
        System.out.println("course service get courses 2");
        Page<Course> pagedResult = this.courseDAO.findAll(paging);
        System.out.println("course service get courses 3");
        if(pagedResult.hasContent()) {
            System.out.println("course service get courses 4");
            return pagedResult.getContent();
        } else {
            System.out.println("course service get courses 5");
            return new ArrayList<Course>();
        }
    }

    public void deleteCourse(Course course){
        this.courseDAO.delete(course);
    }
}
