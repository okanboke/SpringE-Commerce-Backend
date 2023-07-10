package com.Bitirme.Finish2.responses;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.entities.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

        Long categoryId;
        Long productId;
        String categoryName;

                                  //her bir ürün için o ürünün likelarını çekiyoruz

        public CategoryResponse(Category entity) {
            this.categoryId = entity.getCategoryId();
            this.categoryName = entity.getCategoryName();
            this.productId = getProductId();
        }
    }

