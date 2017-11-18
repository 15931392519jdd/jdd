package cn.smbms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.user.ProviderService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	@Resource
	private ProviderService providerService;

	/**
	 * 查询供应商.
	 * @param proCode : 用户编码,默认为String类型的空字符串.可不带参数.
	 * @param proName : 供应商编码,默认为String类型的空字符串.可不带参数.
	 * @param model : ************
	 * @return 返回View是视图(providerlist页面)
	 */
	@RequestMapping(value = "/list.do")
	public String providerList( Model model,
								@RequestParam(value = "proCode", required = false) String proCode,
								@RequestParam(value = "proName", required = false) String proName) {
		List<Provider> lists = providerService.queryList(proCode,proName);
		System.err.println(lists.toString());
		model.addAttribute("providerList", lists);
		model.addAttribute("queryProCode", proCode);
		model.addAttribute("queryProName", proName);
		return "providerlist";
	}
	
	/**
	 * 查询供应商信息.
	 * @param id : 参数ID是传入的参数，用做查询的条件.
	 * @param model : 用来储存数据的Model
	 * @return 
	 */
	@RequestMapping(value="/viewProvider.do",method=RequestMethod.GET)
	public String viewProvider(@RequestParam(value="proid",defaultValue= "0" ,required=true)String id,Model model) {
		if(id == "0") {
			return "redirect:/provder/list.do";
		}
		Provider pro = providerService.queryById(Integer.valueOf(id));
		model.addAttribute("provider", pro);
		return "providerview";
	}
	/**
	 * 修改用户之前的******
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/modifyProvider.do")
	public String modifyProvider(@RequestParam(value="proid",defaultValue= "0" ,required=true)String id,Model model) {
		if(id == "0") {
			return "redirect:/provder/list.do";
		}
		Provider pro = providerService.queryById(Integer.valueOf(id));
		model.addAttribute("provider", pro);
		return "providermodify";
	}
	
	@RequestMapping(value="/upProvider")
	public String Upprovidermodify(HttpServletRequest request,Provider provider) {
		System.err.println(provider);
		if(!Constants.TYPEOFLOGIN(request.getSession())) {
			return "redirect:/";
		}
		User user = (User)request.getSession().getAttribute(Constants.USER_SESSION);
		provider.setModifyby(user.getId());
		provider.setModifyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int i = providerService.updateByProvider(provider);
		System.err.println(i);
		if(i > 0) {
			return "redirect:/provider/list.do";
		}else {
			return "redirect:/provider/modifyProvider.do";
		}
	}
	
	@RequestMapping(value="/delPro.do")
	@ResponseBody
	public Object delProvider(@RequestParam(value="proid",required=true,defaultValue="0")Integer id) {
		Map<String, String> map= new HashMap<>();
		if(id==0) {
			map.put("delResult", "false");
		}else {
			int i=providerService.deleteByPriid(id);
			map.put("delResult", i>0?"true":"notexist");
		}
		return JSONArray.toJSONString(map);
	}
	
	/**
	 * 用来跳转到添加供应商界面.
	 * @return
	 */
	@RequestMapping(value="/providerAdd")
	public String providerAdd() {
		return "provideradd";
	}
	
	@RequestMapping(value="/addProvider.do")
	public String addProvider(Provider provider,HttpSession session) {
		System.err.println(provider.toString());
		User user = (User)session.getAttribute(Constants.USER_SESSION);
		provider.setCreateBy(user.getId());
		provider.setCreationDate(new Date());
		int i = providerService.addProvider(provider);
		System.err.println(i>0?"大于一,天添加成功.":"小于一,添加失败.");
		if(i>0) {
			return "redirect:/provider/list.do";
		}else {
			return "redirect:/provider/providerAdd";
		}
	}
	
	
}
