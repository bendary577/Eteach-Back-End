package com.eteach.eteach.service;

import com.eteach.eteach.dao.CategoryDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public Category createCategory(Category category){
        return this.categoryDAO.save(category);
    }

    public Category updateCategory(Category oldCategory, Category newCategory){
        return this.categoryDAO.save(newCategory);
    }

    public Category getCategory(Long id){
        Category lesson =  this.categoryDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return lesson;
    }

    public List<Category> getAllCategories(){
        return this.categoryDAO.findAll();
    }

    public void deleteCategory(Category category){
        this.categoryDAO.delete(category);
    }
}
