package top.imba77.service;

import top.imba77.pojo.Bill;
import top.imba77.vo.BillVo;

import java.util.List;

public interface BillService {

    List<BillVo> queryBillList(String queryProductName, Integer queryProviderId, Integer queryIsPayment) throws Exception;

    Boolean delBillById(String billId) throws Exception;

    BillVo queryBillById(String billId) throws Exception;

    void updateBillInfo(Bill bill, Long id) throws Exception;

    void addBill(Bill bill, Long id) throws Exception;
}
