package org.example.Error;

public interface CommonError {
    public int getErrorCode();
    public String getErrorMessage();
    public CommonError setMessage(String errMessage);
}
