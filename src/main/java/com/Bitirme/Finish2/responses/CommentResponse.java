package com.Bitirme.Finish2.responses;

import com.Bitirme.Finish2.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse { //commentleri görüntülemek için

    Long id;
    Long userId;
    String userName;
    String text;

    public CommentResponse(Comment entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUserName();
        this.text = entity.getText();
    }
}
