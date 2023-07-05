package com.beyond.watchservice.utils;

/**
 * Name: Result
 * Description: 统一返回结果
 * author: JJ
 * create: 2022/11/17 15:10
 */
public class Result<T> {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    public Result() {
    }

    /**
     *
     * @param data 数据
     * @return 返回数据
     * @param <T> 范型
     */
    protected static <T> Result<T> build(T data){
        Result<T> result = new Result<T>();
        if (data != null){
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(String message){
        Result<T> result = new Result<T>();

        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body,Integer code,String message){
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body,ResultCodeEnum resultCodeEnum){
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     *
     * @param data 数据
     * @return 操作成功
     * @param <T> 范型
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data,ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    public static<T> Result<T> fail(String msg){

        return build(msg);
    }

    /**
     *
     * @param data 数据
     * @return 操作失败
     * @param <T> 范型
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data,ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }


}
