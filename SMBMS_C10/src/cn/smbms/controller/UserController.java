package cn.smbms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.alibaba.fastjson.JSONArray;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<User> queryUserList = new ArrayList<User>();

	@RequestMapping({ "/welcome", "/" })
	public String welcome(String username) {
		logger.info("welcome, username:" + username);
		return "index";
	}


	public UserController() {
		try {
			userList.add(new User(1, "test001", "测试用户001", "1111111", 1,
					new SimpleDateFormat("yyyy-MM-dd").parse("1986-12-10"), "13566669998", "北京市朝阳区北苑", 1, 1, new Date(),
					1, new Date()));
			userList.add(
					new User(2, "zhaoyan", "赵燕", "2222222", 1, new SimpleDateFormat("yyyy-MM-dd").parse("1984-11-10"),
							"18678786545", "北京市海淀区成府路", 1, 1, new Date(), 1, new Date()));
			userList.add(new User(3, "test003", "测试用户003", "3333333", 1,
					new SimpleDateFormat("yyyy-MM-dd").parse("1980-11-11"), "13121334531", "北京市通州北苑", 1, 1, new Date(),
					1, new Date()));
			userList.add(
					new User(4, "wanglin", "王林", "4444444", 1, new SimpleDateFormat("yyyy-MM-dd").parse("1989-09-10"),
							"18965652364", "北京市学院路", 1, 1, new Date(), 1, new Date()));
			userList.add(
					new User(5, "zhaojing", "赵静", "5555555", 1, new SimpleDateFormat("yyyy-MM-dd").parse("1981-08-01"),
							"13054784445", "北京市广安门", 1, 1, new Date(), 1, new Date()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 没有查询条件的情况下，获取userList(公共查询)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		logger.info("无查询条件下，获取userList(公共查询)==== userList");
		model.addAttribute("queryUserList", userList);
		return "user/userlist";
	}

	// 增加查询条件：userName
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@RequestParam(value = "userName", required = false) String userName, Model model) {
		logger.info("查询条件：userName: " + userName + ", 获取userList==== ");
		if (userName != null && !userName.equals("")) {
			for (User user : userList) {
				if (user.getUserName().indexOf(userName) != -1) {
					queryUserList.add(user);
				}
			}
			model.addAttribute("queryUserList", queryUserList);
		} else {
			model.addAttribute("queryUserList", userList);
		}
		return "user/userlist";
	}

	@ExceptionHandler
	public String handlerException(Exception e, HttpServletRequest request) {
		request.setAttribute("error", e);
		return "error";
	}

	@RequestMapping("/userlist.html")
	public String queryUserList(Model model, @RequestParam(value = "queryname", required = false) String queryname,
			@RequestParam(value = "queryUserRole", required = false) String queryUserRole,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {

		// 角色id
		int roleId = 0;
		// 当前页
		int currentPageNo = 1;

		if (null != queryUserRole && !("").equals(queryUserRole)) {
			roleId = Integer.parseInt(queryUserRole);
		}
		if (null != pageIndex && !("").equals(pageIndex)) {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		// 总条数
		int totalNum = userService.getUserCount(queryname, roleId);
		// 总页数
		int totalPage = (totalNum % Constants.pageSize) == 0 ? totalNum / Constants.pageSize
				: (totalNum / Constants.pageSize + 1);

		model.addAttribute("totalCount", totalNum);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("queryUserName", queryname);
		model.addAttribute("queryUserRole", queryUserRole);

		model.addAttribute("roleList", userService.queryRoleList());
		model.addAttribute("userList", userService.getUserList(queryname, roleId, currentPageNo, Constants.pageSize));

		return "userlist";
	}
	
	//跳转到新增用户页面
	@RequestMapping(value = "/useradd.html")
	public String addView() {
		return "useradd";
	}

	/*
	 * @RequestMapping(value = "/useraddsave.html", method = RequestMethod.POST)
	 * public String addUser1( User user, HttpSession session, HttpServletRequest
	 * request,
	 * 
	 * @RequestParam(value = "attach", required = false) MultipartFile attach) {
	 * User user2 = (User) session.getAttribute(Constants.USER_SESSION); if (user2
	 * != null && user2.getId() != 0) { String idPicPath = null; if (attach != null)
	 * { // 文件存储路径 String path = request.getSession().getServletContext()
	 * .getRealPath("statics\\uplodFile"); System.out.println("path----->" + path);
	 * // 文件原是名称 String oldName = attach.getOriginalFilename(); int size = 5000000;
	 * String suffixString = FilenameUtils.getExtension(oldName); if
	 * (attach.getSize() < size) { String fn = System.currentTimeMillis() +
	 * "_person.jpg"; if (suffixString.equalsIgnoreCase("jpg") ||
	 * suffixString.equalsIgnoreCase("png")) { File targetFile = new File(path, fn);
	 * if (!targetFile.exists()) { targetFile.mkdir(); } try {
	 * attach.transferTo(targetFile); } catch (Exception e) { e.printStackTrace();
	 * request.setAttribute("uploadFileError", "上传失败"); return "/useradd"; } } else
	 * { request.setAttribute("uploadFileError", "上传文件格式不符合要求"); return "/useradd";
	 * } idPicPath = path + "//" + fn; } else {
	 * request.setAttribute("uploadFileError", "上传文件过大,不能大于5M"); return "useradd"; }
	 * } user.setIdPicPath(idPicPath); user.setCreatedBy(user2.getId());
	 * user.setCreationDate(new Date()); } else { return
	 * "redirect:/user/useradd.html"; } if (userService.add(user)) { return
	 * "redirect:/user/userlist.html"; } else { return
	 * "redirect:/user/useradd.html"; } }
	 */

	@RequestMapping(value = "/useradd", method = RequestMethod.GET)
	public String addJsp(User user) {
		return "/user/useradd";
	}
	//新增用户
	@RequestMapping(value = "/useradd", method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "/user/useradd";
		} else {
			User user2 = (User) session.getAttribute(Constants.USER_SESSION);
			if (user2 != null && user2.getId() != 0) {
				user.setCreatedBy(user.getId());
				user2.setCreationDate(new Date());
			} else {
				return "redirect:/user/useradd.html";
			}
			if (userService.add(user)) {
				return "redirect:/user/userlist.html";
			} else {
				return "redirect:/user/useradd.html";
			}
		}
	}
	//修改用户
	@RequestMapping("/usermodify/{id}/{flag}")
	public String updateView(Model model, @PathVariable("id") Integer uid, @PathVariable("flag") String flag) {
		User user = new User();
		if (uid != null && uid != 0) {
			user = userService.getUserById(uid);
			model.addAttribute("user", user);
			return "/usermodify";
		} else {
			return "redirect:/user/userlist.html";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String main(HttpSession session) {
		User u = (User) session.getAttribute(Constants.USER_SESSION);
		if (u != null) {
			return "frame";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "usermodifysave.html", method = RequestMethod.POST)
	public String modifyUser(Model model, User user, HttpSession session) {
		User u = (User) session.getAttribute(Constants.USER_SESSION);
		if (u != null && u.getId() != 0) {
			user.setModifyBy(u.getId());
			user.setModifyDate(new Date());
			boolean flag = userService.modify(user);
			if (flag) {
				return "redirect:/user/userlist.html";
			} else {
				model.addAttribute("user", user);
				return "/usermodify";
			}
		} else {
			return "login";
		}

	}

	@RequestMapping(value = "/useraddsave.html", method = RequestMethod.POST)
	public String addUser1(User user, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "attachs", required = false) MultipartFile[] attachs) {
		User u = (User) session.getAttribute(Constants.USER_SESSION);
		String idPicpath = "";
		String idWorkPath = "";
		boolean flag = true;
		if (u != null && u.getId() != null) {
			if (attachs != null && attachs.length > 0) {

				String path = request.getSession().getServletContext().getRealPath("statics\\uplodFile");

				for (int i = 0; i < attachs.length; i++) {
					MultipartFile multipartFile = attachs[i];
					String errorm = "";
					if (i == 0) {
						errorm = "uploadFileError";
					} else {
						errorm = "uploadWpError";
					}
					if (attachs != null && attachs.length > 0) {
						String ooldname = multipartFile.getOriginalFilename();
						String prifix = FilenameUtils.getExtension(ooldname);// 获取文件名的后缀
						int size = 5000000;
						if (multipartFile.getSize() > size) {
							request.setAttribute(errorm, "*照片超过制定大小.");
							flag = false;
						}
						if (prifix.equalsIgnoreCase("jpg") || prifix.equalsIgnoreCase("png")
								|| prifix.equalsIgnoreCase("jpeg") || prifix.equalsIgnoreCase("pneg")) {
							String fileName = System.currentTimeMillis() + Math.random() + Math.random() + "_work.jpg";
							File file = new File(path, fileName);
							if (!file.exists()) {
								file.mkdirs();
							}
							try {
								multipartFile.transferTo(file);
								if (i == 0) {
									idPicpath = path + File.separator + fileName;
								} else {
									idWorkPath = path + File.separator + fileName;
								}
							} catch (IllegalStateException | IOException e) {
								e.printStackTrace();
								request.setAttribute(errorm, "*照片上传失败.");
								flag = false;
							}
						} else {
							request.setAttribute(errorm, "*照片类型不符合要求.");
							flag = false;
						}
					}
				}
			}
		}
		if (flag) {
			user.setIdPicPath(idPicpath);
			user.setWorkPicPath(idWorkPath);
			user.setCreationDate(new Date());
			user.setCreatedBy(u.getId());
			userService.add(user);
			return "redirect:/user/userlist.html";
		} else {
			return "redirect:useradd";
		}
	}

	@RequestMapping(value = "/ucexist", method = RequestMethod.GET)
	@ResponseBody
	public Object userCodeIsExit(@RequestParam(value = "userCode") String userCode) {
		User u = userService.selectUserByUserCode(userCode);
		Map<String, String> map = new HashMap<String, String>();
		if (u == null || u.getId() == null || u.getUserCode() == null) {
			// 当前userCode不存在可以使用.
			map.put("userCode", "noExist");
		} else {
			// 当前userCode存在，不可以使用.
			map.put("userCode", "exist");
		}
		return JSONArray.toJSONString(map);
	}

	@RequestMapping(value = "/view")
	@ResponseBody
	public Object view(@RequestParam(value = "uid", required = true) Integer id) {
		System.out.println("id:" + id);
		User user = userService.getUserById(id);
		System.err.println(user.toString());
		return user;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value = "id", required = true) Integer id) {
		System.err.println("delete.id=" + id);
		Map<String, String> map = new HashMap<>();
		int i = 0;
		try {
			i = userService.delete(id);
		} catch (Exception e) {
			map.put("delResult", "notexist");
		}
		if (i > 0) {
			map.put("delResult", "true");
		} else {
			map.put("delResult", "false");
		}
		return JSONArray.toJSONString(map);
	}

	@RequestMapping("/pwdmodify")
	public String usePwdModify(HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		if (user == null) {
			return "redirect:/index";
		} else {
			return "pwdmodify";
		}
	}

	@RequestMapping(value = "/withPwdmodify")
	@ResponseBody
	public Object pwdmodify(@RequestParam(value = "oldpassword") String oldpassword, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		Map<String, String> map = new HashMap<>();
		// 如果当前Session中没有数据,就是没有登录.返回sessionerror 当前用户失效.
		if (user == null && user.getId() == null) {
			map.put("result", "sessionerror");
			return "sessionerror";
		}
		// 密码为空.返回error
		if (oldpassword == null) {
			map.put("result", "error");
		}
		// 判断Session中的密码和输入的密码进行对比.
		if (user.getUserPassword().equalsIgnoreCase(oldpassword))
			map.put("result", "true");
		else
			map.put("result", "false");
		return JSONArray.toJSONString(map);
	}

	@RequestMapping("/updateWith")
	public String updatePwd(@RequestParam(value = "newpassword") String newpassword, HttpSession session) {
		System.out.println("newpassword:" + newpassword);
		User user = (User) session.getAttribute(Constants.USER_SESSION);
		if (user == null) {
			return "redirect:/index";
		}
		int i = userService.updateWith(newpassword, user.getId());
		if (i > 0) {
			session.removeAttribute(Constants.USER_SESSION);
			return "redirect:/index";
		} else
			return "redirect:/user/pwdmodify";
	}
	
	@RequestMapping("/updateUser")
	public String updateModif(){
		
		return "usermodify";
	}
}
