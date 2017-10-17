package com.jt.manage.controller.web;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller
public class WebItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	/*
	 * 最后返回给前台的数据形式category.getDataService(json)数据
	 * 关键是怎么构建json数据 京淘为此提供了对象类
	 * 第一个用到的对象是Common过程提供的ItemCatResult
	 * 这个对象的作用是构建json串中的data属性
	 * 第二个对象是ItemCatData对象
	 * 这个对象的作用是构建 u n i 属性 其中i要封装某一级商品分类的子集list
	 */
	@RequestMapping("/web/itemcat/all")
	public void queryItemCatJsonp(String callback,HttpServletResponse response){
		ItemCatResult result = itemCatService.jsonp();
		try {
			String json = MAPPER.writeValueAsString(result);
			System.out.println(json);
			String resultJson = callback+"("+json+")";
			//因为前台发过来的是ajax请求所有需要将数据返回到
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(resultJson);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
/*	@RequestMapping("/web/itemcat/all")
	@ResponseBody
	public ItemCatResult queryItemCatJsonp(){
		return itemCatService.jsonp();
	}*/
	
	
}
