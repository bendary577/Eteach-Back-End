package com.eteach.eteach.dao;

import com.eteach.eteach.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("coursedaoimp")
public interface CourseDAO extends PagingAndSortingRepository<Course, Long> {

}