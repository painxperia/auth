package com.zpain.auth.util.mapstruct.impl;

import com.zpain.auth.domain.UserInfo;
import com.zpain.auth.entity.User;
import com.zpain.auth.util.mapstruct.UserConverter;

/**
 * @author zhangjun
 * @date 2021/10/22  10:47
 */
public abstract class UserConverterMapperImpl implements UserConverter {

    private final UserConverter converter;

    public UserConverterMapperImpl(UserConverter converter) {
        this.converter = converter;
    }


}
