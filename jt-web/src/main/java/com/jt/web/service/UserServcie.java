package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 这个类用于前台通过httpclient向单点登录系统提交用户数据
 * @author Administrator
 *
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.User;
@Service
public class UserServcie {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	public void doRegister(User user) throws Exception {
		String url = "http://sso.jt.com/user/register";
		Map<String,String> map = new HashMap<>();
		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		httpClientService.doPost(url, map,"utf-8");
	}

	public String login(User user) {
		String url = "http://sso.jt.com/user/login";
		Map<String,String> map = new HashMap<>();
		//此处是按照接口文档写的
		map.put("u", user.getUsername());
		map.put("p", user.getPassword());
		try {
			//这个json串是单点登录系统返回的SysResult.oK(ticket);
			String json = httpClientService.doPost(url, map,"utf-8");
			JsonNode jn = objectMapper.readTree(json);
			String ticket = jn.get("data").asText();
			return ticket;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
}
