package com.miaoshaproject.miaosha.Error;

public interface CommonError {
    public int getErrorCode();
    public String getErrorMessage();
    public CommonError setMessage(String errMessage);
}
