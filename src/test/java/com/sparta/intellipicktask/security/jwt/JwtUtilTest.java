package com.sparta.intellipicktask.security.jwt;

import com.sparta.intellipicktask.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


class JwtUtilTest {

    private static JwtUtil jwtUtil;
    private String secretKey;

    @Test
    void init() {
        jwtUtil = new JwtUtil();
        secretKey = "askfjasklfjiobfsdj235i023498dsfkjsdflkjasdf902350";
    }

    @Test
    void encodeBase64SecretKeyTest() {
        String secretKey = "askfjasklfjiobfsdj235i023498dsfkjsdflkjasdf902350";
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        System.out.println(base64EncodedSecretKey);

        assertThat(base64EncodedSecretKey).isNotNull();
    }


    @Test
    void createRefreshToken() {
        String username = "testuser";
        UserRoleEnum role = UserRoleEnum.USER;
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1);
        claims.put("roles", List.of("USER"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);

        String accessToken = jwtUtil.createAccessToken(username, role);

        System.out.println(accessToken);

        assertThat(accessToken, notNullValue());
    }

    @Test
    void createRefreshTokenTest() {
        String username = "testuser";
        UserRoleEnum role = UserRoleEnum.USER;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);

        String refreshToken = jwtUtil.createRefreshToken(username, role);

        System.out.println(refreshToken);

        assertThat(refreshToken, notNullValue());
    }
}