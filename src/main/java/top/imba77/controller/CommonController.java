package top.imba77.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.imba77.pojo.User;
import top.imba77.service.CommonService;
import top.imba77.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String doLogin(String userCode, String userPassword, HttpServletRequest req) throws Exception {
        User loginUser = commonService.login(userCode, userPassword);
        HttpSession session = req.getSession();
        session.setAttribute("loginUser", loginUser);
        return "frame";
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String doLogout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }

    @RequestMapping(value = "/jsp/pwdmodify.html", method = RequestMethod.GET)
    public String goModifyPwdPage(HttpSession session) {
        if (CommonUtil.validLogin(session)) {
            return "pwdmodify";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping(value = "/jsp/validpwd/{oldpassword}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> validPwd(@PathVariable("oldpassword") String oldpassword, HttpSession session) throws Exception {
        Map<String, String> map = new HashMap<>();
        User loginUser = (User) session.getAttribute("loginUser");
        if (!(loginUser.getUserName() == null)) {
            boolean isok = commonService.validPwd(oldpassword, loginUser.getId());
            if (isok) {
                map.put("result", "true");
            } else {
                map.put("result", "false");
            }
        } else {
            map.put("result", "sessionerror");
        }
        return map;
    }

    @RequestMapping(value = "/jsp/modifypwd", method = RequestMethod.POST)
    public String modifyPwd(@RequestParam("newpassword") String newPassword, HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        loginUser.setUserPassword(newPassword);
        if (commonService.updatePwdById(loginUser)) {
            session.removeAttribute("loginUser");
        }
        return "redirect:/login.jsp";
    }

}
