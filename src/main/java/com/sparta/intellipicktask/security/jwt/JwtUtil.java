package com.sparta.intellipicktask.security.jwt;

import com.sparta.intellipicktask.entity.UserRoleEnum;
import com.sparta.intellipicktask.enums.ErrorType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component

public class JwtUtil {
    // accessToken 토큰 헤더
    public static final String AUTH_ACCESS_HEADER = "Authorization";
    // refreshToken 토큰 헤더
    public static final String AUTH_REFRESH_HEADER = "RefreshToken";
    // 사용자 권한 키
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // accessToken 만료 시간 (60분)
    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;
    // refreshToken 만료 시간 (2주)
    private final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 로그");

    //중복호출방지(1번만 부를때)
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createAccessToken(String username, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username) //사용자 식별값
                .claim(AUTHORIZATION_KEY, role) //사용자 권한
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date) //발급일
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // accessToken 생성
    public String createRefreshToken(String userId, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public boolean validateToken(HttpServletRequest request, String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            request.setAttribute("jwtException", ErrorType.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            request.setAttribute("jwtException", ErrorType.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            request.setAttribute("jwtException", ErrorType.EXPIRED_JWT);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            request.setAttribute("jwtException", ErrorType.INVALID_JWT);
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request){
        String accessToken = request.getHeader(AUTH_ACCESS_HEADER);
        if(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public String getRefreshTokenFromHeader(HttpServletRequest request){
        String accessToken = request.getHeader(AUTH_REFRESH_HEADER);
        if(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 헤더에 AccessToken 담기
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader(AUTH_ACCESS_HEADER, accessToken);
    }

}

