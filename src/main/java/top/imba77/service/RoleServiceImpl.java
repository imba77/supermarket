package top.imba77.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imba77.dao.RoleMapper;
import top.imba77.pojo.Role;
import top.imba77.pojo.RoleExample;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryRoleList() throws Exception {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }
}
