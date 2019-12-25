package top.imba77.service;

import top.imba77.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> queryRoleList() throws Exception;
}
