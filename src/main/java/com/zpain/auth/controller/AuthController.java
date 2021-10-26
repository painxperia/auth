package com.zpain.auth.controller;

import com.zpain.auth.domain.LoginUser;
import com.zpain.auth.domain.LoginUserInfo;
import com.zpain.auth.domain.Result;
import com.zpain.auth.domain.UserInfo;
import com.zpain.auth.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjun
 * @date 2021/10/21  15:21
 */
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserInfo userInfo) {
        return authService.register(userInfo);
    }

    @PostMapping("/login")
    public Result<LoginUser> login(@RequestBody LoginUserInfo loginUserInfo) {
        return authService.login(loginUserInfo.getUsername(), loginUserInfo.getPassword());
    }
}
