package com.eteach.eteach.dao;

import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRequestDAO extends PagingAndSortingRepository<CourseRequest, Long> {

    @Query("SELECT s FROM CourseRequest s WHERE s.student.id = ?1 and s.course.id = ?2")
    CourseRequest findStudentCourseById(Long studentId, Long courseId);

    @Query("SELECT s FROM CourseRequest s WHERE s.request_code = ?1")
    CourseRequest findByRequest_code(String request_code);
}
