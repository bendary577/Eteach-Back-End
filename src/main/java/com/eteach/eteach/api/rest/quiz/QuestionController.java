package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.quiz.Question;
import com.eteach.eteach.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/questions", produces = "application/json;charset=UTF-8")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    //----------------------------- CREATE A NEW QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/")
    public String postQuestion(@Valid @RequestBody Question question){
        this.questionService.createQuestion(question);
        return "saved";
    }

    //----------------------------- GET ALL QUESTIONS ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    //----------------------------- GET A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable(value = "id") Long id) {
        return questionService.getQuestion(id);
    }

    //----------------------------- UPDATE A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable(value = "id") Long id, @Valid @RequestBody Question newQuestion) {
        Question oldQuestion = questionService.getQuestion(id);
        if(oldQuestion == null){
            throw new ResourceNotFoundException("Question", "id", id);
        }
        return questionService.updateQuestion(oldQuestion, newQuestion);
    }

    //----------------------------- DELETE A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "id") Long id) {
        Question question = questionService.getQuestion(id);
        if(question == null){
            throw new ResourceNotFoundException("Question", "id", id);
        }
        questionService.deleteQuestion(question);
        return ResponseEntity.ok().build();
    }
}
