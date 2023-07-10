package com.Bitirme.Finish2.controllers;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.responses.CategoryResponse;
import com.Bitirme.Finish2.responses.LikeResponse;
import com.Bitirme.Finish2.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();

    }
/*
    @GetMapping
    public List<CategoryResponse> getAllCategories(@RequestParam Optional<Long> categoryId) {
        return categoryService.getAllCategoryParam(categoryId);
    }*/
/*
    @GetMapping("/{categoryId}")
    public Category getOneCategory(@PathVariable Long categoryId) {
        return categoryService.getOneCategoryById(categoryId);
    }
*/
}