package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.GetTeachersByCategoryRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.course.CoursesResponse;
import com.eteach.eteach.http.response.dataResponse.string.StringsResponse;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/teachers", produces = "application/json;charset=UTF-8")
public class TeacherController {

    private final AccountService accountService;
    private final CategoryService categoryService;

    @Autowired
    public TeacherController(AccountService accountService,
                             CategoryService categoryService){
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    //------------------------- CREATE A NEW TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PostMapping("/")
    public String postTeacher(@Valid @RequestBody TeacherAccount teacherAccount){
        this.accountService.saveTeacher(teacherAccount);
        return "saved";
    }

    //---------------------------- UPLOAD TEACHER IMAGE PROFILE --------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/upload/{id}/image/")
    public String uploadImage(@PathVariable(value = "id") Long id, @Valid @NotNull @NotEmpty @RequestParam("image") MultipartFile image) throws IOException {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        //studentAccount.setImage(fileName);
        String imageUploadDirectory = "user-photos/" + teacherAccount.getId();
        // FileUpload.saveFile(imageUploadDirectory, fileName, image);
        this.accountService.saveTeacher(teacherAccount);
        return "saved";
    }

    //------------------------- CREATE ALL TEACHERS WITH PAGINATION -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/")
    public List<TeacherAccount> getAllTeachers(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        List<TeacherAccount> teacherAccounts;
        teacherAccounts = accountService.getAllTeachers(pageNo, pageSize);
        if(teacherAccounts == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", 001);
        }
        return teacherAccounts;
    }

    //------------------------- GET A SINGLE TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER','ROLE_STUDENT')")
    @GetMapping("/{id}")
    public TeacherAccount getTeacher(@PathVariable(value = "id") Long id) {
        return accountService.getTeacher(id);
    }


    //------------------------ UPDATE A TEACHER ACCOUNT ----------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER')")
    @PutMapping("/{id}")
    public TeacherAccount updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody TeacherAccount newTeacherAccount) {
        TeacherAccount oldTeacherAccount = accountService.getTeacher(id);
        if(oldTeacherAccount == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", id);
        }
        return accountService.updateTeacher(oldTeacherAccount, newTeacherAccount);
    }

    //------------------------- DELETE A SINGLE TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", id);
        }
        accountService.deleteTeacher(teacherAccount);
        return ResponseEntity.ok().build();
    }

    //------------------------- GET ALL TEACHER COURSES -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @GetMapping("/{id}/courses/")
    public ResponseEntity<?> getTeacherCourses(@PathVariable(value = "id") Long id) {

        System.out.println("teacher id is : " + id);
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        System.out.println("teacher name is " + teacherAccount.getUser().getUsername());

        if(teacherAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "teacher is not found"));
        }
        List<Course> courses = teacherAccount.getCourses();
        if(courses.size() == 0){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "teacher " + teacherAccount.getUser().getUsername() + " has no courses"));
        }
        return ResponseEntity.ok(new CoursesResponse(HttpStatus.OK, "courses returned successfully", courses));
    }

    //------------------------- GET ALL TEACHERS BY CATEGORY -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PostMapping("/categories/")
    public ResponseEntity<?> getTeachersByCategory(@Valid @NotEmpty @RequestBody GetTeachersByCategoryRequest categoryName) {
        Category category = categoryService.getCategoryByName(categoryName.getCategoryName());
        if(category == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error in loading category data"));
        }
        List<TeacherAccount> teacherAccounts = category.getTeachers();
        List<String> teacherNames = new ArrayList<>();
        if(teacherAccounts == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "this category has no teachers"));
        }
        for(TeacherAccount teacher : teacherAccounts){
            teacherNames.add(teacher.getUser().getUsername());
        }
        return ResponseEntity.ok(new StringsResponse(HttpStatus.OK, "teacher names returned successfully", teacherNames));
    }
}