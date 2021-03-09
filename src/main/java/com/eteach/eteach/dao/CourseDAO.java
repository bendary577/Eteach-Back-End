package com.eteach.eteach.dao;

import com.eteach.eteach.model.course.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDAO extends PagingAndSortingRepository<Course, Long> {

}