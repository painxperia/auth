package com.zpain.auth.util.security;

import com.zpain.auth.domain.UserInfo;
import com.zpain.auth.service.security.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangjun
 * @date 2021/10/20  14:33
 */
public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(UserInfo userInfo) {
        return new JwtUser(
                userInfo.getId(),
                userInfo.getUsername(),
                userInfo.getPassword(),
                userInfo.getEmail(),
                mapToGrantedAuthority(userInfo.getRoles()),
                userInfo.getLastPasswordResetDate()
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthority(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
