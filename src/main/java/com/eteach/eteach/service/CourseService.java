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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        System.out.println("paging : " + paging);
        Page<Course> pagedResult = this.courseDAO.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Course>();
        }
    }

    public void deleteCourse(Course course){
        this.courseDAO.delete(course);
    }

    public String generateCourseCode(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
