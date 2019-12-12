package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.SmbmsRole;
import top.imba77.pojo.SmbmsRoleExample;

import java.util.List;

public interface SmbmsRoleMapper {
    long countByExample(SmbmsRoleExample example);

    int deleteByExample(SmbmsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsRole record);

    int insertSelective(SmbmsRole record);

    List<SmbmsRole> selectByExample(SmbmsRoleExample example);

    SmbmsRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsRole record, @Param("example") SmbmsRoleExample example);

    int updateByExample(@Param("record") SmbmsRole record, @Param("example") SmbmsRoleExample example);

    int updateByPrimaryKeySelective(SmbmsRole record);

    int updateByPrimaryKey(SmbmsRole record);
}