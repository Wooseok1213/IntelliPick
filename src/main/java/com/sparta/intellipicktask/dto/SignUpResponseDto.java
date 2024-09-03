package com.sparta.intellipicktask.dto;

import com.sparta.intellipicktask.entity.User;

public class SignUpResponseDto {
    String username;
    String nickname;

    public SignUpResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}

