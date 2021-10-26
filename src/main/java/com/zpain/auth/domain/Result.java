package com.zpain.auth.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjun
 * @date 2021/6/11  15:15
 */
@Getter
@Setter
public class Result<T> {

    private Integer code;

    private T data;

    private String message;

    public Result(Integer code) {
        this.code = code;
    }

    public static <T> Result<T> success(String message){
        return new Result<T>(1000,message);
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(1000,data);
    }

    public static <T> Result<T> success(){
        return new Result<T>(1000);
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> Result<T> fail(String message){
        return new Result<T>(1001,message);
    }

}
