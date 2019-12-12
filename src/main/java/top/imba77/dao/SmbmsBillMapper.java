package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.SmbmsBill;
import top.imba77.pojo.SmbmsBillExample;

import java.util.List;

public interface SmbmsBillMapper {
    long countByExample(SmbmsBillExample example);

    int deleteByExample(SmbmsBillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsBill record);

    int insertSelective(SmbmsBill record);

    List<SmbmsBill> selectByExample(SmbmsBillExample example);

    SmbmsBill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsBill record, @Param("example") SmbmsBillExample example);

    int updateByExample(@Param("record") SmbmsBill record, @Param("example") SmbmsBillExample example);

    int updateByPrimaryKeySelective(SmbmsBill record);

    int updateByPrimaryKey(SmbmsBill record);
}