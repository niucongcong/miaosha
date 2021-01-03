package org.example.Response.Model;


import lombok.Data;

import java.util.List;

@Data
public class UserPermission {
  private String userName;
  private String roleName;
  private List<String>permissionName;

}
