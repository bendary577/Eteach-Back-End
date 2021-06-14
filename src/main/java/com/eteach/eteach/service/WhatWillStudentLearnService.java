package com.eteach.eteach.service;

import com.eteach.eteach.dao.WhatWillStudentLearnDao;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.WhatWillStudentLearn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatWillStudentLearnService {

    private final WhatWillStudentLearnDao whatWillStudentLearnDao;

    @Autowired
    public WhatWillStudentLearnService(WhatWillStudentLearnDao whatWillStudentLearnDao){
        this.whatWillStudentLearnDao = whatWillStudentLearnDao;
    }

    public WhatWillStudentLearn saveSentence(WhatWillStudentLearn whatWillStudentLearn){
        return this.whatWillStudentLearnDao.save(whatWillStudentLearn);
    }

    public WhatWillStudentLearn updateSentence(WhatWillStudentLearn old, WhatWillStudentLearn newSentence){
        return this.whatWillStudentLearnDao.save(newSentence);
    }

    public WhatWillStudentLearn getOneSentence(Long id){
        return this.whatWillStudentLearnDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WhatWillStudentLearn", "id", id));
    }

    public List<WhatWillStudentLearn> getAllSentences(){
        return this.whatWillStudentLearnDao.findAll();
    }

    public void deleteSentence(WhatWillStudentLearn whatWillStudentLearn){
        this.whatWillStudentLearnDao.delete(whatWillStudentLearn);
    }
}
