package com.Bitirme.Finish2.requests;

import com.Bitirme.Finish2.entities.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProductCreateRequest { //yeni bir ürün eklerken isteği belirttiğimiz varlıklarla eklememiz gerekli...

    Long id;
    String text;
    String title;
    Long userId;
    float price;
    String image;
    String categories;
}
