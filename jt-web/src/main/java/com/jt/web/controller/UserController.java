package com.jt.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserServcie;

import redis.clients.jedis.JedisCluster;



/**
 * 用来响应登录和注册 并转向相关的前台页面
 * @author Administrator
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserServcie userServcie;

	@RequestMapping("/user/login")
	public String goLogin(){
		return "login";
	}
	@RequestMapping("/user/register")
	public String goRegister(){
		return "register";
	}
	@RequestMapping("/user/doRegister")
	@ResponseBody
	public SysResult doRegister(User user){
		try {
			userServcie.doRegister(user);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, "用户注册失败");
		}
	}
	@RequestMapping("/user/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletRequest request,HttpServletResponse response){
		String ticket = userServcie.login(user);
		//Cookie的属性名要和jt.js的属性名对应上  是JT_TICKET
		CookieUtils.setCookie(request, response, "JT_TICKET", ticket);		
		return SysResult.oK();
		
	}

	
	@RequestMapping("/user/logout")
	public String logOut(HttpServletRequest request,HttpServletResponse response){
		CookieUtils.deleteCookie(request, response, "JT_TICKET");
		return "index";
	}
	
	
}
