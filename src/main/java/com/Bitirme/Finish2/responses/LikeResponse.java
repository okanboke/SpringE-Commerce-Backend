package com.Bitirme.Finish2.responses;

import com.Bitirme.Finish2.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {

    Long id;
    Long userId;
    Long productId;

    public LikeResponse(Like entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.productId = entity.getProduct().getId();
    }
}
