package com.eteach.eteach.service;

import com.eteach.eteach.dao.CourseRequestDAO;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CourseRequestService {

    private final CourseRequestDAO courseRequestDAO;

    @Autowired
    public CourseRequestService(CourseRequestDAO courseRequestDAO) {
        this.courseRequestDAO = courseRequestDAO;
    }

    public CourseRequest saveCourseRequest(CourseRequest courseRequest) {
        return this.courseRequestDAO.save(courseRequest);
    }

    public CourseRequest updateCourseRequest(CourseRequest oldCourseRequest, CourseRequest newCourseRequest) {
        return this.courseRequestDAO.save(newCourseRequest);
    }


    public CourseRequest getCourseRequest(Long courseId, Long studentId) {
        return this.courseRequestDAO.findStudentCourseById(courseId,studentId);
    }

    public CourseRequest getCourseRequestByCode(String code) {
        return this.courseRequestDAO.findByRequest_code(code);
    }

    public List<CourseRequest> getAllCourseRequests(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CourseRequest> pagedResult = this.courseRequestDAO.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CourseRequest>();
        }
    }

    public void deleteCourseRequest(CourseRequest courseRequest) {
        this.courseRequestDAO.delete(courseRequest);
    }

    public int generateCourseRequestCode(){
        Random rnd = new Random();
        return  100000 + rnd.nextInt(900000);
    }
}
