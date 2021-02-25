package com.eteach.eteach.dao;

import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizDAO extends JpaRepository<Quiz, Long> {
    List<Quiz> findQuizzesByCourseId(Long courseId);
}
