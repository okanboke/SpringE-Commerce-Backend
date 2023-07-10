package com.Bitirme.Finish2.responses;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.repository.CategoryRepository;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse2 { //frontendde görebilmemiz için objelerimizi response isteğiyle alıyoruz

    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    float price;
    String image;
    List<LikeResponse> productLikes;
    List<Category> categories;

    //her bir ürün için o ürünün likelarını çekiyoruz

    public ProductResponse2(Product entity, List<LikeResponse> likes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.price = entity.getPrice();
        this.productLikes = likes;
        this.image = entity.getImage();
        this.categories = entity.getCategories().stream().collect(Collectors.toUnmodifiableList()); //Bağlı olduğu listeden kategorileri çekiyoruz
    }

}
