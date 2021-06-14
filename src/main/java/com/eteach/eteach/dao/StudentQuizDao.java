package com.eteach.eteach.dao;

import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentQuizDao extends JpaRepository<StudentQuiz, Long> {

    @Query("SELECT s FROM StudentQuiz s WHERE s.student.id = ?1 and s.quiz.id = ?2")
    StudentQuiz findStudentQuizById(Long studentId,  Long quizId);
}
