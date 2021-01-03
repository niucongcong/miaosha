package org.example.Service.ServiceImp.ServiceImp;

import org.example.Dao.RoleDOMapper;
import org.example.DataObject.RoleDO;
import org.example.Service.ServiceImp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {


    @Autowired
    private RoleDOMapper roleDOMapper;
    @Override
    public RoleDO selectByPrimaryKey(Integer roleId) {
        RoleDO roleDO=roleDOMapper.selectByPrimaryKey(roleId);
        return roleDO;
    }
}
