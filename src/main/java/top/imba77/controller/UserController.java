package top.imba77.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imba77.pojo.Role;
import top.imba77.pojo.User;
import top.imba77.service.RoleService;
import top.imba77.service.UserService;
import top.imba77.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsp/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/userlist.html")
    public String queryUserList(HttpServletRequest req
            , @RequestParam(value = "queryname", required = false) String queryname
            , @RequestParam(value = "queryUserRole", required = false) Integer roleId
            , @RequestParam(value = "pageIndex", required = false) Integer pageIndex
    ) throws Exception {
        int totalCount = userService.queryUserCount();
        int pageSize = 6;
        int totalPageCount = totalCount % 6 == 0 ? totalCount / 6 : (totalCount / 6) + 1;
        int currentPageNo = 1;
        if (pageIndex != null) {
            currentPageNo = pageIndex;
        }
        List<UserVo> userList = userService.queryUserList(queryname, roleId, currentPageNo, pageSize);
        req.setAttribute("userList", userList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        List<Role> roleList = roleService.queryRoleList();
        req.setAttribute("roleList", roleList);
        return "userlist";
    }

    @RequestMapping("/userview/{userId}")
    public String queryUserInfo(@PathVariable("userId") String userId, HttpServletRequest req) throws Exception {
        UserVo userVo = userService.queryUserById(userId);
        req.setAttribute("user", userVo);
        return "userview";
    }

    @RequestMapping("/deluser/{userId}")
    @ResponseBody
    public String delUser(@PathVariable("userId") String userId) throws Exception {
        Boolean b = userService.delUserById(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("delResult", b.toString());
        String resultJson = new ObjectMapper().writeValueAsString(result);
        return resultJson;
    }

    @RequestMapping("/usermodify/{userId}")
    public String userModify(@PathVariable("userId") String userId, HttpServletRequest req) throws Exception {
        UserVo userVo = userService.queryUserById(userId);
        req.setAttribute("user", userVo);
        return "usermodify";
    }

    @RequestMapping("/modifyuser")
    public String modifyUser(User user, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        Boolean b = userService.updateUserInfo(user, loginUser.getId());
        return "userlist";
    }

    @RequestMapping("/rolelist")
    @ResponseBody
    public List<Role> queryRoleList() throws Exception {
        List<Role> roleList = roleService.queryRoleList();
        return roleList;
    }

    @RequestMapping("/add.html")
    public String goAddUser() {
        return "useradd";
    }

    @RequestMapping("/ucexist/{userCode}")
    @ResponseBody
    public Map<String, String> userCodeExist(@PathVariable("userCode") String userCode) throws Exception {
        Boolean ishave = userService.findUserByUserCode(userCode);
        Map<String, String> map = new HashMap<>();
        if (ishave) {
            map.put("userCode", "exist");
        } else {
            map.put("userCode", "");
        }
        return map;
    }

    @RequestMapping("/adduser")
    public String addUser(User user, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Long id = loginUser.getId();
        System.out.println(id);
        Boolean b = userService.addUser(user, id);
        return "userlist";
    }

    @RequestMapping("/pwdmodify.html")
    public String goModifyPwd() {
        return "pwdmodify";
    }

    @RequestMapping("/validpwd/{oldpassword}")
    @ResponseBody
    public Map<String, String> validPwd(@PathVariable("oldpassword") String oldpassword, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        Map<String, String> map = new HashMap<>();
        if (loginUser.getUserName() == null) {
            map.put("result", "sessionerror");
            return map;
        }
        Boolean ishave = userService.validPwd(oldpassword, loginUser.getId());
        if (ishave) {
            map.put("result", "true");
        } else {
            map.put("result", "false");
        }
        return map;
    }

    @RequestMapping("/modifypwd")
    public String modifyPwd(@RequestParam("newpassword") String newPassword, HttpServletRequest req) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        loginUser.setUserPassword(newPassword);
        Boolean flag = userService.updatePwdById(loginUser);
        return "forward:/login.jsp";
    }
}
