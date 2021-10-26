package com.zpain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 登录时请求参数
 *
 * @author zhangjun
 * @date 2021/10/21  16:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    private String username;
    private String password;

}
