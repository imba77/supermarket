package top.imba77.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.imba77.pojo.User;
import top.imba77.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginAndLogoutController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String doLogin(String userCode, String userPassword, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        if (null == userCode && "".equals(userCode.trim()) && null == userPassword && "".equals(userPassword.trim())) {
            req.setAttribute("error", "用户名或密码为必填项");
            return "forward:/login.jsp";
        }
        User loginUser = userService.login(userCode, userPassword);
        session.setAttribute("loginUser", loginUser);
        return "frame";
    }

    @RequestMapping(value = "/jsp/logout.do", method = RequestMethod.GET)
    public String doLogout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }
}
