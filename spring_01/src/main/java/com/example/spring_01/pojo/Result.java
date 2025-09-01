package com.example.spring_01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    public static Result success(){
        return new Result(1,"success",null);
    }

    /*public static Result success(Object data){

        return new Result(1,"success",data);
    }*/

    public static <E> Result<E> success(E data){
        return new Result<>(1,"操作成功",data);
    }

    public static Result error(String msg){
        return new Result(0,msg,null);
    }

}
