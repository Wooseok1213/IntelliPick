package com.sparta.intellipicktask.controller;

import com.sparta.intellipicktask.dto.LoginRequestDto;
import com.sparta.intellipicktask.dto.LoginResponsetDto;
import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.security.UserDetailsImpl;
import com.sparta.intellipicktask.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = service.signup(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.login(requestDto, userDetails.getUser().getId());
        return ResponseEntity.ok().body("로그인 되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.logout(userDetails.getUser().getId());
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }
}