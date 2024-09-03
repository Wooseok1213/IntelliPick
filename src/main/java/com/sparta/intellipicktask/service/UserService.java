package com.sparta.intellipicktask.service;

import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.enums.ErrorType;
import com.sparta.intellipicktask.exception.CustomException;
import com.sparta.intellipicktask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResponseDto signup(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        Optional<User> checkUsername = repository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        // Nickname 중복확인
        Optional<User> checkNickname = repository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_NICKNAME);
        }
        // Email 중복확인
        Optional<User> checkEmail = repository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        }
        // 사용자 등록
        User user = new User(username, nickname, password, email, role);
        repository.save(user);
        return new SignUpResponseDto(user);


    }
}