package com.Bitirme.Finish2.responses;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.entities.Like;
import com.Bitirme.Finish2.entities.Product;

import com.Bitirme.Finish2.repository.CategoryRepository;
import com.Bitirme.Finish2.services.CategoryService;
import lombok.Data;

@Data
public class ProductResponse {                                      //frontendde görebilmemiz için objelerimizi response isteğiyle alıyoruz

    private CategoryRepository categoryRepository;

    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    float price;
    String image;
    List<LikeResponse> productLikes;


    //her bir ürün için o ürünün likelarını çekiyoruz

    public ProductResponse(Product entity, List<LikeResponse> likes) {


        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.price = entity.getPrice();
        this.productLikes = likes;
        this.image = entity.getImage();
    }
    }

