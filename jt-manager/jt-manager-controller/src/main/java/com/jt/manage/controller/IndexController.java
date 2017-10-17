package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
/*	@RequestMapping("/index")
	public String index(){
		//跳转系统首页
		return "index";
	}*/
/*	@RequestMapping("/page/item-add")
	public String itemAdd(){
		return "item-add";
	}
	@RequestMapping("/page/item-list")
	public String itemList(){
		return "item-list";
	}*/                     
	@RequestMapping("/page/{moudleName}")
	public String moudle(@PathVariable String moudleName){
		return moudleName;
	}
	
}
