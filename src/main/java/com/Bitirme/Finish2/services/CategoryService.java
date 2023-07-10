
package com.Bitirme.Finish2.services;


import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.entities.Like;
import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.repository.CategoryRepository;
import com.Bitirme.Finish2.repository.LikeRepository;
import com.Bitirme.Finish2.responses.CategoryResponse;
import com.Bitirme.Finish2.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();

    }

/*
    public List<CategoryResponse> getAllCategoryParam(Optional<Long> categoryId) {
        List<Category> list;
        if(categoryId.isPresent()) {
            list = categoryRepository.findByCategoryId(categoryId.get());
        }else
            list = categoryRepository.findAll();
        return list.stream().map(category -> new CategoryResponse(category)).collect(Collectors.toList());


    }*/
//

}


