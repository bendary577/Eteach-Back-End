package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.quiz.Choice;
import com.eteach.eteach.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/choices", produces = "application/json;charset=UTF-8")
public class ChoiceController {

    private final ChoiceService choiceService;

    @Autowired
    public ChoiceController(ChoiceService choiceService){
        this.choiceService = choiceService;
    }

    //----------------------------- CREATE A NEW Choice ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/")
    public String postChoice(@Valid @RequestBody Choice choice){
        this.choiceService.createChoice(choice);
        return "saved";
    }

    //----------------------------- GET ALL Choices ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/")
    public List<Choice> getAllChoices() {
        return choiceService.getAllChoices();
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
