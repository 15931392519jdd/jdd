package cn.smbms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	/**
	 * 跳转到订单主页面
	 * @return
	 */
	@RequestMapping("/bill.do")
	public String billList(){
		
		return "billlist";
	}
}
