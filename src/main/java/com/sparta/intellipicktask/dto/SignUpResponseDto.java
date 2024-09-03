package com.sparta.intellipicktask.dto;

import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import lombok.Getter;

@Getter

public class SignUpResponseDto {
    String username;
    String nickname;
    UserRoleEnum userRoleEnum;

    public SignUpResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.userRoleEnum = user.getRole();
    }
}

