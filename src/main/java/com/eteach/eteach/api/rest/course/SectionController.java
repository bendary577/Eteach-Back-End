package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.dataRequest.course.AddSectionRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.course.Section;
import com.eteach.eteach.service.CourseService;
import com.eteach.eteach.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/sections", produces = "application/json;charset=UTF-8")
public class SectionController {

    private final SectionService sectionService;
    private final CourseService courseService;

    @Autowired
    public SectionController(SectionService sectionService,
                             CourseService courseService){
        this.sectionService = sectionService;
        this.courseService = courseService;
    }

    //----------------------------- CREATE A NEW SECTION ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    @PostMapping("/{courseId}")
    public ResponseEntity<?> postSection(@PathVariable Long courseId, @Valid @RequestBody AddSectionRequest addSectionRequest){
        Course course = courseService.getCourse(courseId);
        Section section = new Section();
        section.setTitle(addSectionRequest.getTitle());
        course.getSections().add(section);
        section.setCourse(course);
        this.sectionService.saveSection(section);
        this.courseService.saveCourse(course);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "section saved successfully"));
    }

    //------------------------------ GET ALL SECTIONS --------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{courseId}")
    public List<Section> getAllSections(@PathVariable Long courseId) {
        return sectionService.getAllSections(courseId);
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
