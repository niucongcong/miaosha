package org.example.Service.ServiceImp;

import org.example.DataObject.RoleDO;

public interface RoleService {
    RoleDO selectByPrimaryKey(Integer roleId);
}
