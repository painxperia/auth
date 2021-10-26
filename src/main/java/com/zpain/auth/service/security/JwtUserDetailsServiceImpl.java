package com.zpain.auth.service.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zpain.auth.domain.UserInfo;
import com.zpain.auth.entity.Role;
import com.zpain.auth.entity.User;
import com.zpain.auth.entity.UserRole;
import com.zpain.auth.mapper.RoleMapper;
import com.zpain.auth.mapper.UserMapper;
import com.zpain.auth.mapper.UserRoleMapper;
import com.zpain.auth.service.redis.RedisManager;
import com.zpain.auth.util.mapstruct.UserConverter;
import com.zpain.auth.util.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangjun
 * @date 2021/10/20  14:41
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", s);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("No user with name %s", s));
        } else {
            String username = String.format("%s-user", user.getUsername());
            JwtUser jwtUser = redisManager.getCache(username);
            if (Objects.isNull(jwtUser)) {
                QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
                userRoleQueryWrapper.eq("user_id", user.getId());
                UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
                Role role = roleMapper.selectById(userRole.getRoleId());
                List<String> list = new ArrayList<>();
                list.add("ROLE_" + role.getRoleName());
                UserInfo userInfo = UserConverter.INSTANCE.toUser(user);
                userInfo.setRoles(list);
                jwtUser = JwtUserFactory.create(userInfo);
                redisManager.setCacheDuration(username, jwtUser, Duration.ofMinutes(30));
            }
            return jwtUser;
        }
    }
}
