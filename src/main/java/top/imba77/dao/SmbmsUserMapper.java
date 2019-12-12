package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.SmbmsUser;
import top.imba77.pojo.SmbmsUserExample;

import java.util.List;

public interface SmbmsUserMapper {
    long countByExample(SmbmsUserExample example);

    int deleteByExample(SmbmsUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsUser record);

    int insertSelective(SmbmsUser record);

    List<SmbmsUser> selectByExample(SmbmsUserExample example);

    SmbmsUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByExample(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByPrimaryKeySelective(SmbmsUser record);

    int updateByPrimaryKey(SmbmsUser record);
}