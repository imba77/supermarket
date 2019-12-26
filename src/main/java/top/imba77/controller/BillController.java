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
import top.imba77.util.CommonUtil;
import top.imba77.vo.BillVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();
        if (!CommonUtil.validLogin(session)) {
            return "redirect:/login.jsp";
        }
        List<Provider> providerList = providerService.queryProviderList();
        req.setAttribute("providerList", providerList);
        List<BillVo> billList = billService.queryBillList(queryProductName, queryProviderId, queryIsPayment);
        req.setAttribute("billList", billList);
        return "billlist";
    }

    @RequestMapping("/billview/{billId}")
    public String queryUserInfo(@PathVariable("billId") String billId, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            BillVo billVo = billService.queryBillById(billId);
            req.setAttribute("bill", billVo);
            return "billview";
        }
        return "redirect:/login.jsp";
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
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            BillVo billVo = billService.queryBillById(billId);
            req.setAttribute("bill", billVo);
            return "billmodify";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/modifybill")
    public String modifyBill(Bill bill, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("loginUser");
        billService.updateBillInfo(bill, user.getId());
        return "forward:billlist.html";
    }

    @RequestMapping("/add.html")
    public String goAddUser(HttpSession session) {
        if (CommonUtil.validLogin(session)) {
            return "billadd";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/addbill")
    public String addBill(Bill bill, HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        billService.addBill(bill, loginUser.getId());
        return "forward:billlist.html";
    }

    @RequestMapping("/providerlist")
    @ResponseBody
    public List<Provider> queryProviderList() throws Exception {
        List<Provider> providerList = providerService.queryProviderList();
        return providerList;
    }

}
