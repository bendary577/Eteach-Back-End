package com.eteach.eteach.api.rest;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public String postCategory(@Valid @RequestBody Category category){
        this.categoryService.createCategory(category);
        return "saved";
    }

    //------------------------------ GET ALL CATEGORIES --------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    //------------------------------ GET A SINGLE CATEGORY ------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable(value = "id") Long id) {
        return categoryService.getCategory(id);
    }

    //------------------------------ UPDATE A CATEGORY ------------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long id, @Valid @RequestBody Category newCategory) {
        Category oldCategory = categoryService.getCategory(id);
        if(oldCategory == null){
            throw new ResourceNotFoundException("Category", "id", id);
        }
        return categoryService.updateCategory(oldCategory, newCategory);
    }

    //------------------------------ DELETE A SINGLE CATEGORY ---------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getCategory(id);
        if(category == null){
            throw new ResourceNotFoundException("Category", "id", id);
        }
        categoryService.deleteCategory(category);
        return ResponseEntity.ok().build();
    }
}
