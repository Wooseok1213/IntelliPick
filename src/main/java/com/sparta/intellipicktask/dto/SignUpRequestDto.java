package com.sparta.intellipicktask.dto;

import com.sparta.intellipicktask.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String email;

//    public SignUpRequestDto(User user) {
//        this.username = user.getUsername();
//        this.nickname = user.getNickname();
//        this.password = user.getPassword();
//        this.email = user.getEmail();
//
//    }
}