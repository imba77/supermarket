package top.imba77.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imba77.pojo.Bill;
import top.imba77.pojo.Provider;
import top.imba77.pojo.User;
import top.imba77.service.BillService;
import top.imba77.service.ProviderService;
import top.imba77.vo.BillVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsp/bill")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    @RequestMapping("billlist.html")
    public String queryBillList(HttpServletRequest req
            , @RequestParam(value = "queryProductName", required = false) String queryProductName
            , @RequestParam(value = "queryProviderId", required = false) Integer queryProviderId
            , @RequestParam(value = "queryIsPayment", required = false) Integer queryIsPayment
    ) throws Exception {
        List<BillVo> billList = billService.queryBillList(queryProductName, queryProviderId, queryIsPayment);
        req.setAttribute("billList", billList);
        List<Provider> providerList = providerService.queryProviderList();
        req.setAttribute("providerList", providerList);
        return "billlist";
    }

    @RequestMapping("/billview/{billId}")
    public String queryUserInfo(@PathVariable("billId") String billId, HttpServletRequest req) throws Exception {
        BillVo billVo = billService.queryBillById(billId);
        req.setAttribute("bill", billVo);
        return "billview";
    }

    @RequestMapping("/delbill/{billId}")
    @ResponseBody
    public String delUser(@PathVariable("billId") String billId) throws Exception {
        Boolean b = billService.delBillById(billId);
        Map<String, Object> result = new HashMap<>();
        result.put("delResult", b.toString());
        String resultJson = new ObjectMapper().writeValueAsString(result);
        return resultJson;
    }

    @RequestMapping("/billmodify/{billId}")
    public String billModify(@PathVariable("billId") String billId, HttpServletRequest req) throws Exception {
        BillVo billVo = billService.queryBillById(billId);
        req.setAttribute("bill", billVo);
        return "billmodify";
    }

    @RequestMapping("/modifybill")
    public String modifyBill(Bill bill, HttpServletRequest req) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        Boolean b = billService.updateBillInfo(bill, user.getId());
        return "billlist";
    }

    @RequestMapping("/providerlist")
    @ResponseBody
    public List<Provider> queryProviderList() throws Exception {
        List<Provider> providerList = providerService.queryProviderList();
        return providerList;
    }

    @RequestMapping("/add.html")
    public String goAddUser() {
        return "billadd";
    }

    @RequestMapping("/addbill")
    public String addBill(Bill bill, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        Boolean b = billService.addBill(bill, loginUser.getId());
        return "billlist";
    }


}
