package top.imba77.util;

import top.imba77.pojo.User;

import javax.servlet.http.HttpSession;

public class CommonUtil {

    public static boolean validLogin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (null == loginUser.getUserName() || "".equals(loginUser.getUserName().trim())) {
            return false;
        }
        return true;
    }

}
