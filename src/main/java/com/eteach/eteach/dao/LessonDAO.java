package com.eteach.eteach.dao;

import com.eteach.eteach.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("lessondaoimp")
public interface LessonDAO extends JpaRepository<Lesson, Long> {

}