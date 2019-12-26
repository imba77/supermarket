package top.imba77.service;

import top.imba77.pojo.User;

public interface CommonService {

    User login(String userCode, String userPassword) throws Exception;

    boolean validPwd(String oldpassword, Long id) throws Exception;

    boolean updatePwdById(User user) throws Exception;

}
