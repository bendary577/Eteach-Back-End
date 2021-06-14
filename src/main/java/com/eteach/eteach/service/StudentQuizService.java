package com.eteach.eteach.service;

import com.eteach.eteach.dao.StudentQuizDao;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentQuizService {

    private final StudentQuizDao studentQuizDao;

    @Autowired
    public StudentQuizService(StudentQuizDao studentQuizDao){
        this.studentQuizDao = studentQuizDao;
    }

    public StudentQuiz saveStudentQuiz(StudentQuiz studentQuiz){
        return this.studentQuizDao.save(studentQuiz);
    }

    public StudentQuiz updateStudentQuiz(StudentQuiz oldStudentQuiz, StudentQuiz newStudentQuiz){
        return this.studentQuizDao.save(newStudentQuiz);
    }

    public StudentQuiz getStudentQuiz(Long studentId, Long quizId){
        return this.studentQuizDao.findStudentQuizById(studentId, quizId);
    }

    public List<StudentQuiz> getAllStudentQuizzes(){
        return this.studentQuizDao.findAll();
    }

    public void deleteStudentQuiz(StudentQuiz studentQuiz){
        this.studentQuizDao.delete(studentQuiz);
    }
}
