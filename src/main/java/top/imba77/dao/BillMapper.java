package top.imba77.dao;

import org.apache.ibatis.annotations.Param;
import top.imba77.pojo.Bill;
import top.imba77.pojo.BillExample;
import top.imba77.vo.BillVo;

import java.util.List;
import java.util.Map;

public interface BillMapper {
    long countByExample(BillExample example);

    int deleteByExample(BillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    int insertSelective(Bill record);

    List<Bill> selectByExample(BillExample example);

    Bill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Bill record, @Param("example") BillExample example);

    int updateByExample(@Param("record") Bill record, @Param("example") BillExample example);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List<BillVo> selectBillList(Map<String, Object> param);
}