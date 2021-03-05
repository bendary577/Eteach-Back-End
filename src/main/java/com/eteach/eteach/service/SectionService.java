package com.eteach.eteach.service;

import com.eteach.eteach.dao.SectionDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private SectionDAO sectionDAO;

    @Autowired
    public SectionService(SectionDAO sectionDAO){
        this.sectionDAO = sectionDAO;
    }

    public Section createSection(Section section){
        return this.sectionDAO.save(section);
    }

    public Section updateSection(Section oldSection, Section newSection){
        return this.sectionDAO.save(newSection);
    }

    public Section getSection(Long id){
        Section section =  this.sectionDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
        return section;
    }

    public List<Section> getAllSections(){
        return this.sectionDAO.findAll();
    }

    public void deleteSection(Section section){
        this.sectionDAO.delete(section);
    }



}
