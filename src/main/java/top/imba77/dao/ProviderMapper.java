package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.Provider;
import top.imba77.pojo.ProviderExample;
import top.imba77.vo.ProviderVo;

import java.util.List;
import java.util.Map;

public interface ProviderMapper {
    long countByExample(ProviderExample example);

    int deleteByExample(ProviderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Provider record);

    int insertSelective(Provider record);

    List<Provider> selectByExample(ProviderExample example);

    Provider selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Provider record, @Param("example") ProviderExample example);

    int updateByExample(@Param("record") Provider record, @Param("example") ProviderExample example);

    int updateByPrimaryKeySelective(Provider record);

    int updateByPrimaryKey(Provider record);

    List<ProviderVo> selectProviderList(Map<String, Object> param);
}