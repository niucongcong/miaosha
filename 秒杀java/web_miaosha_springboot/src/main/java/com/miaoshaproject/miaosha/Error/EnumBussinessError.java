package com.miaoshaproject.miaosha.Error;

public enum EnumBussinessError implements  CommonError{
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_HAS_EXIST(20002,"用户已经注册"),
    USER_LOGIN_FAIL(20003,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20004,"用户还未登陆"),
    STOCK_NOT_ENOUGH(30001,"库存不足"),
    MQ_SEND_FAIL(30002,"库存异步消息失败"),
    ;

    EnumBussinessError(int errCode,String errMessage){
        this.errCode=errCode;
        this.errMessage=errMessage;
    }
    private int errCode;
    private String errMessage;

    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errMessage;
    }

    @Override
    public CommonError setMessage(String errMessage) {
        this.errMessage=errMessage;
        return this;
    }
}
