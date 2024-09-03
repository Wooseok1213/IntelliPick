package com.sparta.intellipicktask.service;

import com.sparta.intellipicktask.dto.LoginRequestDto;
import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.security.jwt.JwtUtil;
import com.sparta.intellipicktask.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpResponseDto signup(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = repository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // Nickname 중복확인
        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = repository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 Nickname 입니다.");
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;

        // 사용자 등록
        User user = new User(username, password, nickname, role);
        repository.save(user);
        return new SignUpResponseDto(user);
    }
}