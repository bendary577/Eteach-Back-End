package com.eteach.eteach.dao;

import com.eteach.eteach.model.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Long> {
}
