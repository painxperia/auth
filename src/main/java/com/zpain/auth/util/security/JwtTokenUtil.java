package com.zpain.auth.util.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zpain.auth.domain.TokenInfo;
import com.zpain.auth.service.redis.RedisManager;
import com.zpain.auth.service.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjun
 * @date 2021/10/21  13:33
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Autowired
    private RedisManager redisManager;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date nowDate, Date lastPasswordReset) {
        return (lastPasswordReset != null && nowDate.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(8);
        Date created = new Date();
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, created);
        String token = generateToken(claims, expirationDate);
        setCache(userDetails.getUsername(), token, created, expirationDate);
        return token;
    }

    public void setCache(String username, String token, Date created, Date expiration) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUsername(username);
        tokenInfo.setToken(token);
        tokenInfo.setCreated(created);
        tokenInfo.setExpiration(expiration);
        String key = String.format("%s-token", username);
        redisManager.setCache(key, JSON.toJSONString(tokenInfo));
    }

    String generateToken(Map<String, Object> claims, Date expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims, new Date(System.currentTimeMillis() + expiration));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        Date created = getCreatedDateFromToken(token);
        String key = String.format("%s-token", username);
        String tokenInfo = redisManager.getCache(key);
        //&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)) && StringUtils.isNoneEmpty(tokenInfo)
                && isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }

}
