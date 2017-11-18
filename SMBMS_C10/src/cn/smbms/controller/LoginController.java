package cn.smbms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;

@Controller
public class LoginController {

	@Resource
	private UserService userService;


	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String userPassword,
			HttpServletRequest request, HttpSession session) {
		System.err.println("Login.do class");
		User u = userService.login(userCode, userPassword);
		if (u != null) {
			// 正确的页面
			session.setAttribute(Constants.USER_SESSION, u);
			return "redirect:/user/index";
		} else {
			// 重新登录
			request.setAttribute("error", "用户名或者密码错误");
			// return "login";
			throw new RuntimeException("用户名或者密码错误");
		}
	}

}
