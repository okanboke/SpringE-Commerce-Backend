package com.Bitirme.Finish2.repository;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.entities.Like;
import com.Bitirme.Finish2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByCategoryName(String categoryName); //name ile ürünleri çekme

    public Category findByCategoryId(Long categoryId);



    //List<Category> findByCategoryId(Long categoryId);





}
