package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.User;
import top.imba77.pojo.UserExample;
import top.imba77.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UserVo> selectUserList(Map<String, Object> param);
}