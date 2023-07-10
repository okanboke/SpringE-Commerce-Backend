package com.Bitirme.Finish2.responses;

import com.Bitirme.Finish2.entities.User;

import lombok.Data;

@Data
public class UserResponse { //user avatarını değiştirmek için bu sınıfı kullanıyoruz.

    Long id;
    int avatarId;
    String userName;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.avatarId = entity.getAvatar();
        this.userName = entity.getUserName();
    }
}
