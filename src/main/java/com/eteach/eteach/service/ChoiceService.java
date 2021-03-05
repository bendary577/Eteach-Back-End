package com.eteach.eteach.service;

import com.eteach.eteach.dao.ChoiceDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.quiz.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceService {

    private ChoiceDAO ChoiceDAO;

    @Autowired
    public ChoiceService(ChoiceDAO ChoiceDAO){
        this.ChoiceDAO = ChoiceDAO;
    }

    public Choice createChoice(Choice choice){
        return this.ChoiceDAO.save(choice);
    }

    public Choice updateChoice(Choice oldChoice, Choice newChoice){
        return this.ChoiceDAO.save(newChoice);
    }

    public Choice getChoice(Long id){
        Choice choice =  this.ChoiceDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", id));
        return choice;
    }

    public List<Choice> getAllChoices(){
        return this.ChoiceDAO.findAll();
    }

    public void deleteChoice(Choice choice){
        this.ChoiceDAO.delete(choice);
    }
}
