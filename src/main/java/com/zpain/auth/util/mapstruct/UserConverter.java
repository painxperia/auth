package com.zpain.auth.util.mapstruct;

import com.zpain.auth.domain.UserInfo;
import com.zpain.auth.entity.User;
import com.zpain.auth.util.mapstruct.impl.UserConverterMapperImpl;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhangjun
 * @date 2021/10/20  16:00
 */
@Mapper
@DecoratedWith(UserConverterMapperImpl.class)
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 用户转换
     *
     * @param User
     * @return
     */
//    @Mapping(target = "username",source = "username")
//    @BeanMapping(ignoreByDefault = true)
    UserInfo toUser(User User);

    /**
     * 用户转换
     *
     * @param userInfo
     * @return
     */
    User infoToUser(UserInfo userInfo);


}
