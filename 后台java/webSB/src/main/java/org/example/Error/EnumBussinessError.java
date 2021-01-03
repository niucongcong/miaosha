package org.example.Error;

public enum EnumBussinessError implements  CommonError{
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_HAS_EXIST(20002,"用户已经注册"),
    CODE_NOT_SAME(20005,"请输入正确的验证码"),
    USER_LOGIN_FAIL(20003,"用户名或密码不正确"),
    USER_NOT_LOGIN(20004,"用户还未登陆"),
    STOCK_NOT_ENOUGH(30001,"库存不足"),
    PUBLISH_FAILED(30002,"发布失败"),
    ITEM_NOT_EXITS(30003,"商品不存在"),
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
