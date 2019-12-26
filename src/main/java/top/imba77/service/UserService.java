package top.imba77.service;

import top.imba77.pojo.User;
import top.imba77.vo.UserVo;

import java.util.List;

public interface UserService {

    List<UserVo> queryUserList(String queryname, Integer roleId, Integer currentPageNo, Integer pageSize) throws Exception;

    int queryUserCount() throws Exception;

    UserVo queryUserById(String userId) throws Exception;

    boolean delUserById(String userId) throws Exception;

    void addUser(User user, Long id) throws Exception;

    Boolean findUserByUserCode(String userCode) throws Exception;

    void updateUserInfo(User user, Long id) throws Exception;
}
