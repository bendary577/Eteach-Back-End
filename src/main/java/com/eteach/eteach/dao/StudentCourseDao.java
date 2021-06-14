package com.eteach.eteach.dao;

import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseDao extends JpaRepository<StudentCourse, Long> {

    @Query("SELECT s FROM StudentCourse s WHERE s.student.id = ?1 and s.course.id = ?2")
    StudentCourse findStudentCourseById(Long studentId, Long courseId);
}
