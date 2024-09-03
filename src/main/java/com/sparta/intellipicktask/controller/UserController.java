package com.sparta.intellipicktask.controller;

import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = service.signup(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
}