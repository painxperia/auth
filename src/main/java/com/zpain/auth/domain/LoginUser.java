package com.zpain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户信息+token
 *
 * @author zhangjun
 * @date 2021/10/21  16:04
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private UserInfo userInfo;
    private String token;
}
