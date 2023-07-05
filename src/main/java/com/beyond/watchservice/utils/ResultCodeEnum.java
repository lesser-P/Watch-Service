package com.beyond.watchservice.utils;

/**
 * Name: ResultCodeEnum
 * Description: 统一返回结果状态信息类
 * author: JJ
 * create: 2022/11/17 15:19
 */
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    ACCESS_IS_DENIED( 216, "未经过身份验证,访问被拒绝"),
    ACCOUNT_STOP( 217, "账号已停用"),
    NODE_ERROR( 218, "该节点下有子节点，不可以删除")
    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
