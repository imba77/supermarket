package top.imba77.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imba77.dao.RoleMapper;
import top.imba77.dao.UserMapper;
import top.imba77.pojo.Role;
import top.imba77.pojo.User;
import top.imba77.pojo.UserExample;
import top.imba77.vo.UserVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User login(String userCode, String userPassword) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(userPassword);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new Exception("请输入正确的用户名或密码");
        }
    }

    @Override
    public List<UserVo> queryUserList(String queryname, Integer roleId, Integer currentPageNo, Integer pageSize) throws Exception {
        int startIndex = (currentPageNo - 1) * pageSize;
        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", startIndex);
        param.put("pageSize", pageSize);
        param.put("queryName", queryname);
        param.put("roleId", roleId);
        return userMapper.selectUserList(param);
    }

    @Override
    public int queryUserCount() throws Exception {
        UserExample example = new UserExample();
        return (int) userMapper.countByExample(example);
    }

    @Override
    public UserVo queryUserById(String userId) throws Exception {
        User user = userMapper.selectByPrimaryKey(Long.parseLong(userId));
        Role role = roleMapper.selectByPrimaryKey(Long.valueOf(user.getUserRole()));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setUserRoleName(role.getRoleName());
        return userVo;
    }

    @Override
    public boolean delUserById(String userId) throws Exception {
        int i = userMapper.deleteByPrimaryKey(Long.parseLong(userId));
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean addUser(User user, Long id) throws Exception {
        user.setCreatedBy(id);
        user.setCreationDate(new Date());
        int i = userMapper.insert(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean findUserByUserCode(String userCode) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePwdById(User user) throws Exception {
        int i = userMapper.updateByPrimaryKey(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean validPwd(String oldpassword, Long id) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        if (user.getUserPassword().equals(oldpassword)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateUserInfo(User user, Long id) throws Exception {
//        User tempUser = userMapper.selectByPrimaryKey(user.getId());
//        user.setCreatedBy(tempUser.getCreatedBy());
//        user.setCreationDate(tempUser.getCreationDate());
        user.setModifyBy(id);
        user.setModifyDate(new Date());
        int i = userMapper.updateByPrimaryKey(user);
        if (i > 0) {
            return true;
        }
        return false;
    }
}
