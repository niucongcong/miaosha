package org.example.Error;

public class BusinessException extends  Exception implements  CommonError{

    private CommonError commonError;

    public BusinessException(CommonError commonError){
        super();
        this.commonError=commonError;
    }

    public BusinessException(CommonError commonError,String errMessgae){
        super();
        this.commonError=commonError;
        this.commonError.setMessage(errMessgae);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return this.commonError.getErrorMessage();
    }

    @Override
    public CommonError setMessage(String errMessage) {
        this.commonError.setMessage(errMessage);
        return this;
    }
}
