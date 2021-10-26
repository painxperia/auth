package com.zpain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author zhangjun
 * @date 2021/10/20  14:15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Date lastPasswordResetDate;
    private List<String> roles;

}
