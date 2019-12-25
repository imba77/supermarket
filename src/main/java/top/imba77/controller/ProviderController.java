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
import top.imba77.service.ProviderService;
import top.imba77.vo.BillVo;
import top.imba77.vo.ProviderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsp/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @RequestMapping("providerlist.html")
    public String queryProviderList(HttpServletRequest req
            , @RequestParam(value = "queryProCode", required = false) String queryProCode
            , @RequestParam(value = "queryProName", required = false) String queryProName
    ) throws Exception {
        List<ProviderVo> providerlist = providerService.queryProviderVoList(queryProCode, queryProName);
        req.setAttribute("providerList", providerlist);
        return "providerlist";
    }

    @RequestMapping("/providerview/{proId}")
    public String queryUserInfo(@PathVariable("proId") String proId, HttpServletRequest req) throws Exception {
        ProviderVo providerVo = providerService.queryProviderById(proId);
        req.setAttribute("provider", providerVo);
        return "providerview";
    }

    @RequestMapping("/delprovider/{proId}")
    @ResponseBody
    public String delProvider(@PathVariable("proId") String proId) throws Exception {
        Boolean b = providerService.delProviderById(proId);
        Map<String, Object> result = new HashMap<>();
        result.put("delResult", b.toString());
        String resultJson = new ObjectMapper().writeValueAsString(result);
        return resultJson;
    }

    @RequestMapping("/providermodify/{proId}")
    public String providerModify(@PathVariable("proId") String proId, HttpServletRequest req) throws Exception {
        ProviderVo providerVo = providerService.queryProviderById(proId);
        req.setAttribute("provider", providerVo);
        return "providermodify";
    }

    @RequestMapping("/modifyprovider")
    public String modifyBill(Provider provider, HttpServletRequest req) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        Boolean b = providerService.updateProviderInfo(provider, user.getId());
        return "providerlist";
    }

    @RequestMapping("/add.html")
    public String goAddProvider() {
        return "provideradd";
    }

    @RequestMapping("/addprovider")
    public String addProvider(Provider provider, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        Boolean b = providerService.addProvider(provider, loginUser.getId());
        return "providerlist";
    }
}
