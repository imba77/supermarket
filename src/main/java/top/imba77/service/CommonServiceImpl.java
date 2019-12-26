package top.imba77.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imba77.dao.UserMapper;
import top.imba77.pojo.User;
import top.imba77.pojo.UserExample;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userCode, String userPassword) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(userPassword);
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() == 1) {
            return userList.get(0);
        } else {
            throw new Exception("用户名或密码错误");
        }
    }

    @Override
    public boolean validPwd(String oldpassword, Long id) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        if (user.getUserPassword().equals(oldpassword)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePwdById(User user) throws Exception {
        int isok = userMapper.updateByPrimaryKey(user);
        if (isok > 0) {
            return true;
        }
        return false;
    }

}
