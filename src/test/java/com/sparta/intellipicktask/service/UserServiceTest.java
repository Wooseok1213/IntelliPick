package com.sparta.intellipicktask.service;

import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.dto.SignUpResponseDto;
import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional

class UserServiceTest {
    @Mock private UserRepository userRepository;
    @InjectMocks private UserService userService;
    @Spy private BCryptPasswordEncoder passwordEncoder;

    @Test
    void signup() throws Exception {
        // given
        String username = "username";
        String nickname = "nickname";
        String email = "email@example.com";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        SignUpRequestDto requestDto = new SignUpRequestDto();

        requestDto.setUsername(username);
        requestDto.setNickname(nickname);
        requestDto.setEmail(email);
        requestDto.setPassword(password);

        User user = new User(username, nickname, encodedPassword, email, UserRoleEnum.USER);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        // when
        SignUpResponseDto responseDto = userService.signup(requestDto);
        // then
        assertThat(responseDto.getUsername()).isEqualTo(username);
        assertThat(responseDto.getNickname()).isEqualTo(nickname);
        // verify
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).findByNickname(nickname);
        verify(userRepository, times(1)).findByEmail(email);
    }
}
//    @InjectMocks
//    private UserService userService;
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void signUp_Success() {
//        // Given
//        SignUpRequestDto requestDto = new SignUpRequestDto();
//        requestDto.setUsername("username");
//        requestDto.setNickname("nickname");
//        requestDto.setPassword("password");
//        requestDto.setEmail("test@email.com");
//
//        User user = new User("test11",
//                "test1",
//                "test12",
//                "test@email.com",
//                UserRoleEnum.USER);
//
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
//        when(userRepository.findByNickname("nickname")).thenReturn(Optional.empty());
//        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        // When
//        SignUpResponseDto responseDto = userService.signup(requestDto);
//
//        // Then
//        assertNotNull(responseDto);
//        assertEquals("username", responseDto.getUsername());
//        assertEquals("nickname", responseDto.getNickname());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void signUp_UsernameAlreadyExists() {
//        // Given
//        SignUpRequestDto requestDto = new SignUpRequestDto();
//        requestDto.setUsername("username");
//        requestDto.setNickname("nickname");
//        requestDto.setPassword("password");
//        requestDto.setEmail("email@example.com");
//
//        when(userRepository.findByUsername("username")).thenReturn(Optional.of(new User()));
//
//        // When / Then
//        CustomException exception = assertThrows(CustomException.class, () -> userService.signup(requestDto));
//        assertEquals(ErrorType.DUPLICATE_ACCOUNT_ID, exception.getErrorType());
//    }
