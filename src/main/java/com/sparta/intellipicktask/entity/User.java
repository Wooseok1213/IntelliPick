package com.sparta.intellipicktask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String nickname;

    private String password;

    @Email
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Setter
    private String refreshToken;


    public User(String username, String nickname, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken != null && this.refreshToken.equals(refreshToken);
    }
}