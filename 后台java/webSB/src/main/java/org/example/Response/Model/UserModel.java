package org.example.Response.Model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserModel  implements Serializable {
    private static final long serialVersionUID = -7898194272883238670L;

    public static final String OBJECT_KEY = "USER";

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String  role_name;

    private String token;
}
