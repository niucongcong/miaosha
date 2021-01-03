package org.example.Response;

import lombok.Data;

@Data
public class CommonReturnType {
    private String status;
    private Object data;
    public static CommonReturnType create(Object data){
        return CommonReturnType.create(data, "success");
    }
    public static CommonReturnType create(Object data,String status){
        CommonReturnType commonReturnType=new CommonReturnType();
        commonReturnType.setStatus(status);;
        commonReturnType.setData(data);
        return  commonReturnType;
    }
}
