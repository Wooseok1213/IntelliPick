package com.sparta.intellipicktask.service;

import com.sparta.intellipicktask.dto.SignUpRequestDto;
import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.exception.CustomException;
import com.sparta.intellipicktask.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void signup() throws Exception {
        // given
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        SignUpRequestDto requestDto = new SignUpRequestDto();
        given(userRepository.saveUser(any())).willReturn(1);
        given(userRepository.existsByEmail("email")).willReturn(false);
        given(passwordEncoder.encode("password")).willReturn("password");
        // when
        userService.signup(requestDto);
        // then
        verify(userRepository, times(1)).save(captor.capture());
        User user = captor.getValue();
        assertThat(requestDto.getUsername()).isEqualTo(user.getUsername());
        assertThat(requestDto.getNickname()).isEqualTo(user.getNickname());
        assertThat(requestDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("회원가입 진행시 중복된 아이디가 있는경우")
    public void 유저네임중복() {
        //given
        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        given(userRepository.existsByUsername("username")).willReturn(true);
        //when & then
        assertThatThrownBy(() -> userService.signup(signUpRequest))
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("회원가입 진행시 이미 이메일이 가입되어 있는경우")
    public void SignUp_IfExistsEmail() {
        //given
        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        given(userRepository.existsByEmail("email")).willReturn(true);
        //when & then
        assertThatThrownBy(() -> userService.signup(signUpRequest))
                .isInstanceOf(CustomException.class);
    }

    private void setUser() {
        User user = new User("Test",
                "test",
                "tset123",
                "test@email.com",
                    UserRoleEnum.USER);
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
