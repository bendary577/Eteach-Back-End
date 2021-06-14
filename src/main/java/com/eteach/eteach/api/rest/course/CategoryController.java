package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.dataRequest.category.AddCategoryRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.category.CategoriesCoursesResponse;
import com.eteach.eteach.http.response.dataResponse.category.CategoriesResponse;
import com.eteach.eteach.http.response.dataResponse.category.CategoryResponse;
import com.eteach.eteach.http.response.dataResponse.category.util.Category;
import com.eteach.eteach.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/categories", produces = "application/json;charset=UTF-8")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //----------------------------- CREATE A NEW CATEGORY ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PostMapping("/")
    public ResponseEntity<?> postCategory(@Valid @RequestBody AddCategoryRequest addCategoryRequest){
        com.eteach.eteach.model.course.Category category = new com.eteach.eteach.model.course.Category();
        category.setName(addCategoryRequest.getName());
        category.setDescription(addCategoryRequest.getDescription());
        this.categoryService.saveCategory(category);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Category have been saved successfully"));
    }

    //------------------------------ GET ALL CATEGORIES --------------------------------------------------
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories() {
        List<com.eteach.eteach.model.course.Category> categories = categoryService.getAllCategories();
        if(categories == null){
            return ResponseEntity.ok(new CategoriesResponse(HttpStatus.NO_CONTENT, "error in returning available categories", null));
        }
        return ResponseEntity.ok(new CategoriesResponse(HttpStatus.OK, "categories returned successfully", categories));
    }
    //------------------------------ GET ALL CATEGORIES --------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/courses/")
    public ResponseEntity<?> getAllCategoriesCourses() {
        List<com.eteach.eteach.model.course.Category> categories = categoryService.getAllCategories();
        if(categories == null){
            return ResponseEntity.ok(new CategoriesResponse(HttpStatus.NO_CONTENT, "error in returning available categories", null));
        }
        List<Category> categoryCoursesList = new ArrayList<>();

        for(com.eteach.eteach.model.course.Category category : categories){
            Category category1 = new Category();
            category1.setId(category.getId());
            category1.setName(category.getName());
            category1.setDescription(category.getDescription());
            category1.setCourses(category.getCourses());
            categoryCoursesList.add(category1);
        }

        return ResponseEntity.ok(new CategoriesCoursesResponse(HttpStatus.OK, "categories courses returned successfully", categoryCoursesList));
    }

    //------------------------------ GET A SINGLE CATEGORY ------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable(value = "id") Long id) {
        com.eteach.eteach.model.course.Category category = categoryService.getCategory(id);
        if(category == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "error in returning available category"));
        }
        return ResponseEntity.ok(new CategoryResponse(HttpStatus.OK, "category returned successfully", category));
    }

    //------------------------------ UPDATE A CATEGORY ------------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PutMapping("/{id}")
    public com.eteach.eteach.model.course.Category updateCategory(@PathVariable(value = "id") Long id, @Valid @RequestBody com.eteach.eteach.model.course.Category newCategory) {
        com.eteach.eteach.model.course.Category oldCategory = categoryService.getCategory(id);
        if(oldCategory == null){
            throw new ResourceNotFoundException("Category", "id", id);
        }
        return categoryService.updateCategory(oldCategory, newCategory);
    }

    //------------------------------ DELETE A SINGLE CATEGORY ---------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        com.eteach.eteach.model.course.Category category = categoryService.getCategory(id);
        if(category == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "category is not found"));
        }
        categoryService.deleteCategory(category);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "category deleted successfully"));
    }
}
