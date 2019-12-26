package top.imba77.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imba77.dao.BillMapper;
import top.imba77.dao.ProviderMapper;
import top.imba77.pojo.Bill;
import top.imba77.pojo.Provider;
import top.imba77.vo.BillVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<BillVo> queryBillList(String queryProductName, Integer queryProviderId, Integer queryIsPayment) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("queryProductName", queryProductName);
        param.put("queryProviderId", queryProviderId);
        param.put("queryIsPayment", queryIsPayment);
        return billMapper.selectBillList(param);
    }

    @Override
    public Boolean delBillById(String billId) throws Exception {
        int i = billMapper.deleteByPrimaryKey(Long.parseLong(billId));
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public BillVo queryBillById(String billId) throws Exception {
        Bill Bill = billMapper.selectByPrimaryKey(Long.parseLong(billId));
        Provider provider = providerMapper.selectByPrimaryKey(Long.valueOf(Bill.getProviderId()));
        BillVo billVo = new BillVo();
        BeanUtils.copyProperties(Bill, billVo);
        billVo.setProviderName(provider.getProName());
        return billVo;
    }

    @Override
    public void updateBillInfo(Bill bill, Long id) throws Exception {
        Bill tempBill = billMapper.selectByPrimaryKey(bill.getId());
        bill.setCreatedBy(tempBill.getCreatedBy());
        bill.setCreationDate(tempBill.getCreationDate());
        bill.setModifyBy(id);
        bill.setModifyDate(new Date());
        billMapper.updateByPrimaryKey(bill);
    }

    @Override
    public void addBill(Bill bill, Long id) throws Exception {
        bill.setCreatedBy(id);
        bill.setCreationDate(new Date());
        billMapper.insert(bill);
    }
}
