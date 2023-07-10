package com.Bitirme.Finish2.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {

    Long id;
    Long userId;
    Long productId;
    String text;

}
