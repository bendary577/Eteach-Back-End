package com.eteach.eteach.dao;

import com.eteach.eteach.model.quiz.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceDAO extends JpaRepository<Choice, Long> {
}
