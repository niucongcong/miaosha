package com.miaoshaproject.miaosha.Service.Model;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserModel  implements Serializable {
    private static final long serialVersionUID = -7898194272883238670L;

    public static final String OBJECT_KEY = "USER";

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "年龄不能不填写")
    private Integer gender;

    @NotNull(message = "年龄不能不写")
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    private String telphone;


    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String encrptPassword;
}
