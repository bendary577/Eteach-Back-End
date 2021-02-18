package com.eteach.eteach.dao;

import com.eteach.eteach.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("coursedaoimp")
public interface CourseDAO extends JpaRepository<Course, Long> {

}