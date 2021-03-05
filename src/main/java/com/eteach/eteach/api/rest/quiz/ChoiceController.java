package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.quiz.Choice;
import com.eteach.eteach.model.quiz.Question;
import com.eteach.eteach.service.ChoiceService;
import com.eteach.eteach.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/api/v1/choices", produces = "application/json;charset=UTF-8")
public class ChoiceController {

    private final ChoiceService choiceService;
    private final QuestionService questionService;

    @Autowired
    public ChoiceController(ChoiceService choiceService, QuestionService questionService){
        this.choiceService = choiceService;
        this.questionService = questionService;
    }

    //----------------------------- CREATE A NEW Choice ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/{questionId}")
    public ResponseEntity<?> postChoices(@PathVariable Long questionId, @Valid @RequestBody List<Choice> choices){
        Question question = questionService.getQuestion(questionId);
        choices.stream()
                .peek(choice -> {
                    choice.setQuestion(question);
                    this.choiceService.createChoice(choice);
                    question.getChoices().add(choice);
                    this.questionService.saveQuestion(question);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "choices saved successfully"));
    }

    //----------------------------- GET ALL Choices ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{questionId}")
    public List<Choice> getAllChoices(@PathVariable Long questionId) {
        return choiceService.getAllChoices(questionId);
    }

    //----------------------------- GET A SINGLE Choice ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Choice getChoice(@PathVariable(value = "id") Long id) {
        return choiceService.getChoice(id);
    }

    //----------------------------- UPDATE A SINGLE Choice ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Choice updateChoice(@PathVariable(value = "id") Long id, @Valid @RequestBody Choice newChoice) {
        Choice oldChoice = choiceService.getChoice(id);
        if(oldChoice == null){
            throw new ResourceNotFoundException("Choice", "id", id);
        }
        return choiceService.updateChoice(oldChoice, newChoice);
    }

    //----------------------------- DELETE A SINGLE Choice ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChoice(@PathVariable(value = "id") Long id) {
        Choice choice = choiceService.getChoice(id);
        if(choice == null){
            throw new ResourceNotFoundException("Choice", "id", id);
        }
        choiceService.deleteChoice(choice);
        return ResponseEntity.ok().build();
    }
}
