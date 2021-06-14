package com.eteach.eteach.dao;

import com.eteach.eteach.model.compositeKeys.CourseRatingKey;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRatingDao extends JpaRepository<CourseRating, Long> {

    @Query("SELECT s FROM CourseRating s WHERE s.student.id = ?1 and s.course.id = ?2")
    CourseRating findCourseRatingById(Long studentId, Long courseId);
}
