package com.eteach.eteach.service;

import com.eteach.eteach.dao.CourseRatingDao;
import com.eteach.eteach.model.compositeKeys.CourseRatingKey;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRatingService {

    private final CourseRatingDao courseRatingDao;

    @Autowired
    public CourseRatingService(CourseRatingDao courseRatingDao) {
        this.courseRatingDao = courseRatingDao;
    }

    public CourseRating saveCourseRating(CourseRating courseRating) {
        return this.courseRatingDao.save(courseRating);
    }

    public CourseRating updateCourseRating(CourseRating oldCourseRating, CourseRating newCourseRating) {
        return this.courseRatingDao.save(newCourseRating);
    }

    public CourseRating getCourseRating(Long studentId, Long courseId) {
        return this.courseRatingDao.findCourseRatingById(studentId, courseId);
    }

    public List<CourseRating> getAllCourseRatings() {
        return this.courseRatingDao.findAll();
    }

    public void deleteCourseRating(CourseRating courseRating) {
        this.courseRatingDao.delete(courseRating);
    }
}
