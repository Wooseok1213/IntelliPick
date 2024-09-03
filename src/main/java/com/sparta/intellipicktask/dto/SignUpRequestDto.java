package com.sparta.intellipicktask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignUpRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
}