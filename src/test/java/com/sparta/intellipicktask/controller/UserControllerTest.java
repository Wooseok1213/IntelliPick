//package com.sparta.intellipicktask.controller;
//
//import com.sparta.intellipicktask.dto.SignUpRequestDto;
//import com.sparta.intellipicktask.dto.SignUpResponseDto;
//import com.sparta.intellipicktask.entity.UserRoleEnum;
//import com.sparta.intellipicktask.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static jdk.jfr.internal.jfc.model.Constraint.any;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//    @InjectMocks
//    private UserController userController;
//    @Mock
//    private UserService userService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    void 회원가입성공() throws Exception {
//        SignUpRequestDto request = SignUpRequestDto();
//        SignUpResponseDto response = SignUpResponseDto();
//
//        doReturn(response).when(userService)
//                .signUp(any(SignUpRequestDto.class));
//    }
//
//    private <T> MockedStatic<T> doReturn(SignUpResponseDto response) {
//    }
//}
