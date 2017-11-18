package cn.smbms.tools;

import javax.servlet.http.HttpSession;

import cn.smbms.pojo.User;

public class Constants {

	public final static String USER_SESSION = "userSession";

	public final static int pageSize = 5;

	public static boolean TYPEOFLOGIN(HttpSession session) {
		User user = (User) session.getAttribute(USER_SESSION);
		if (user == null)
			return false;
		else
			return true;
	}
}
