package com.zpain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangjun
 * @date 2021/10/25  9:11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo implements Serializable {

    private String username;
    private String token;
    private Date created;
    private Date expiration;

}
