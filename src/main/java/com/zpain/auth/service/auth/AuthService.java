package com.zpain.auth.service.auth;

import com.zpain.auth.domain.LoginUser;
import com.zpain.auth.domain.Result;
import com.zpain.auth.domain.UserInfo;

/**
 * @author zhangjun
 * @date 2021/10/21  14:54
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param userInfo
     * @return
     */
    Result<String> register(UserInfo userInfo);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    Result<LoginUser> login(String username, String password);

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);


}
