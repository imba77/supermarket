package top.imba77.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imba77.pojo.Provider;
import top.imba77.pojo.User;
import top.imba77.service.ProviderService;
import top.imba77.util.CommonUtil;
import top.imba77.vo.ProviderVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();
        if (!CommonUtil.validLogin(session)) {
            return "redirect:/login.jsp";
        }
        List<ProviderVo> providerlist = providerService.queryProviderVoList(queryProCode, queryProName);
        req.setAttribute("providerList", providerlist);
        return "providerlist";
    }

    @RequestMapping("/providerview/{proId}")
    public String queryUserInfo(@PathVariable("proId") String proId, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            ProviderVo providerVo = providerService.queryProviderById(proId);
            req.setAttribute("provider", providerVo);
            return "providerview";
        }
        return "redirect:/login.jsp";
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
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            ProviderVo providerVo = providerService.queryProviderById(proId);
            req.setAttribute("provider", providerVo);
            return "providermodify";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/modifyprovider")
    public String modifyBill(Provider provider, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("loginUser");
        providerService.updateProviderInfo(provider, user.getId());
        return "forward:providerlist.html";
    }

    @RequestMapping("/add.html")
    public String goAddProvider(HttpSession session) {
        if (CommonUtil.validLogin(session)) {
            return "provideradd";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/addprovider")
    public String addProvider(Provider provider, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        providerService.addProvider(provider, loginUser.getId());
        return "forward:providerlist.html";
    }
}
