package com.eteach.eteach.service;

import com.eteach.eteach.dao.QuestionDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.quiz.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    public QuestionService(QuestionDAO questionDAO){
        this.questionDAO = questionDAO;
    }

    public Question createQuestion(Question question){
        return this.questionDAO.save(question);
    }

    public Question updateQuestion(Question oldQuestion, Question newQuestion){
        return this.questionDAO.save(newQuestion);
    }

    public Question getQuestion(Long id){
        Question question =  this.questionDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        return question;
    }

    public List<Question> getAllQuestions(){
        return this.questionDAO.findAll();
    }

    public void deleteQuestion(Question question){
        this.questionDAO.delete(question);
    }
}
