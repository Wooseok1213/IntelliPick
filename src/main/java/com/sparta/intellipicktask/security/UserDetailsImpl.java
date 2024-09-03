package com.sparta.intellipicktask.security;

import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    // 권한 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum userRole = user.getRole();
        String authority = userRole.toString();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }
}
