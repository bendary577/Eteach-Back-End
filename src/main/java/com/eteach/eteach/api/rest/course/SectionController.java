package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Section;
import com.eteach.eteach.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/sections", produces = "application/json;charset=UTF-8")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    //----------------------------- CREATE A NEW SECTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/")
    public String postSection(@Valid @RequestBody Section section){
        this.sectionService.createSection(section);
        return "saved";
    }

    //------------------------------ GET ALL SECTIONS --------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/")
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }


    //------------------------------ GET A SINGLE SECTION ------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Section getSection(@PathVariable(value = "id") Long id) {
        return sectionService.getSection(id);
    }


    //------------------------------ UPDATE A SECTION ------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Section updateSection(@PathVariable(value = "id") Long id, @Valid @RequestBody Section newSection) {
        Section oldSection = sectionService.getSection(id);
        if(oldSection == null){
            throw new ResourceNotFoundException("Section", "id", id);
        }
        return sectionService.updateSection(oldSection, newSection);
    }

    //------------------------------ DELETE A SINGLE SECTION ---------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSection(@PathVariable(value = "id") Long id) {
        Section section = sectionService.getSection(id);
        if(section == null){
            throw new ResourceNotFoundException("Section", "id", id);
        }
        sectionService.deleteSection(section);
        return ResponseEntity.ok().build();
    }
}
