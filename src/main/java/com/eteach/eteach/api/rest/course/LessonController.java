package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Lesson;
import com.eteach.eteach.model.course.Section;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Material;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.service.FileService;
import com.eteach.eteach.service.LessonService;
import com.eteach.eteach.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/api/v1/lessons", produces = "application/json;charset=UTF-8")
public class LessonController {

    private final LessonService lessonService;
    private final SectionService sectionService;
    private final FileService fileService;

    @Autowired
    public LessonController(LessonService lessonService,
                            SectionService sectionService,
                            FileService fileService){
        this.lessonService = lessonService;
        this.sectionService = sectionService;
        this.fileService = fileService;
    }

    //----------------------------- CREATE A NEW LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value="/{sectionId}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> postLesson(@PathVariable Long sectionId,
                                        @Valid @RequestBody Lesson lesson,
                                        @RequestPart("video") @Valid @NotNull @NotEmpty MultipartFile video) throws IOException {
        Section section = sectionService.getSection(sectionId);
        section.getLessons().add(lesson);
        lesson.setSection(section);

        String contentType = video.getContentType();
        Long size = video.getSize();
        if (!fileService.validateVideoFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "video is not valid"));
        }

        Path path = Paths.get(lesson.getVideoDirPath());
        Video lessonVideo = fileService.createVideoFile(video, path);
        lesson.setVideo(lessonVideo);

        this.lessonService.saveLesson(lesson);
        this.sectionService.saveSection(section);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "lesson saved successfully"));
    }
    //------------------------------- ADD LESSON MATERIALS -------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value="/{lessonId}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addLessonMaterial(@PathVariable Long lessonId,
                                        @RequestPart("material") @Valid @NotNull @NotEmpty List<MultipartFile> materials) throws IOException {
        Lesson lesson = this.lessonService.getLesson(lessonId);
        if (lesson == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "lesson not found"));
        }
        //for each material file uploaded
        materials.stream()
                .peek(material->{
                    String contentType = material.getContentType();
                    Long size = material.getSize();
                    if (!fileService.validateMaterialFile(contentType, size)) {
                        System.out.println("material is not validated");
                    }
                    Path path = Paths.get(lesson.getMaterialDirPath());
                    try {
                        Material lessonMaterial = fileService.createMaterialFile(material, path);
                        lesson.getMaterials().add(lessonMaterial);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).collect(Collectors.toList());
        //save the updated lesson
        this.lessonService.saveLesson(lesson);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "lesson saved successfully"));
    }


    //----------------------------- GET ALL LESSONS ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    //----------------------------- GET A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable(value = "id") Long id) {
        return lessonService.getLesson(id);
    }

    //----------------------------- UPDATE A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson newLesson) {
        Lesson oldLesson = lessonService.getLesson(id);
        if(oldLesson == null){
            throw new ResourceNotFoundException("lesson", "id", id);
        }
        return lessonService.updateLesson(oldLesson, newLesson);
    }

    //----------------------------- DELETE A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable(value = "id") Long id) {
        Lesson lesson = lessonService.getLesson(id);
        if(lesson == null){
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
        lessonService.deleteLesson(lesson);
        return ResponseEntity.ok().build();
    }

}