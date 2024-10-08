package com.sparta.intellipicktask.service;

import com.sparta.intellipicktask.dto.LoginRequestDto;
import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.enums.ErrorType;
import com.sparta.intellipicktask.exception.CustomException;
import com.sparta.intellipicktask.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

    // 로그아웃
    @Transactional
    public boolean logout(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
        -> new CustomException(ErrorType.NOT_FOUND_USER));
        return user.logout();
    }

    @Transactional
    public boolean login(LoginRequestDto requestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new CustomException(ErrorType.NOT_FOUND_USER));
        if (!user.getUsername().equals(requestDto.getUsername())) {
            new CustomException(ErrorType.INVALID_USERNAME);
        }
        if (!user.getPassword().equals(requestDto.getPassword())) {
            new CustomException(ErrorType.INVALID_PASSWORD);
        }
        return user.login();
    }
}