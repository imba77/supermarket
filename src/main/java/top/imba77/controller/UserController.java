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
import top.imba77.util.CommonUtil;
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
        HttpSession session = req.getSession();
        if (!CommonUtil.validLogin(session)) {
            return "redirect:/login.jsp";
        }
        if (null != queryname || !("".equals(queryname))) {
            req.setAttribute("queryname", queryname);
        }
        if (null != roleId) {
            req.setAttribute("roleId", roleId);
        }
        int totalCount = userService.queryUserCount();
        req.setAttribute("totalCount", totalCount);
        int pageSize = 6;
        int totalPageCount = totalCount % 6 == 0 ? totalCount / 6 : (totalCount / 6) + 1;
        req.setAttribute("totalPageCount", totalPageCount);
        int currentPageNo = 1;
        if (pageIndex != null) {
            currentPageNo = pageIndex;
        }
        req.setAttribute("currentPageNo", currentPageNo);
        List<UserVo> userList = userService.queryUserList(queryname, roleId, currentPageNo, pageSize);
        req.setAttribute("userList", userList);
        List<Role> roleList = roleService.queryRoleList();
        req.setAttribute("roleList", roleList);
        return "userlist";
    }

    @RequestMapping("/userview/{userId}")
    public String queryUserInfo(@PathVariable("userId") String userId, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            UserVo userVo = userService.queryUserById(userId);
            req.setAttribute("user", userVo);
            return "userview";
        }
        return "redirect:/login.jsp";
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
        HttpSession session = req.getSession();
        if (CommonUtil.validLogin(session)) {
            UserVo userVo = userService.queryUserById(userId);
            req.setAttribute("user", userVo);
            return "usermodify";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/modifyuser")
    public String modifyUser(User user, HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        userService.updateUserInfo(user, loginUser.getId());
        return "forward:userlist.html";
    }

    @RequestMapping("/add.html")
    public String goAddUser(HttpSession session) {
        if (CommonUtil.validLogin(session)) {
            return "useradd";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/adduser")
    public String addUser(User user, HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        userService.addUser(user, loginUser.getId());
        return "forward:userlist.html";
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

    @RequestMapping("/rolelist")
    @ResponseBody
    public List<Role> queryRoleList() throws Exception {
        return roleService.queryRoleList();
    }
}
