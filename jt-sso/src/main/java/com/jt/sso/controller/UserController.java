package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserServcie;

import redis.clients.jedis.JedisCluster;
@Controller
public class UserController {
	@Autowired
	private UserServcie userService;
	@Autowired
	private JedisCluster jedisCluster;
	/*
	 * 用来响应用户在注册时前台发起的校验响应
		这个请求是跨域请求 所在参数需要 callback
		type=1表示用户ming
		type=2表示手机
		type=3表示邮箱
		因为是jsonp的跨域请求 所以返回数据的形式 callback(json)
		可以通过 SysResult.ok(),来构造
		 SysResult.ok(false)表式可以注册
		 SysResult.ok(true)表式不可以注册
		 true和false的结果可以去数据库查询		 
	 */
	//http://sso.jt.com/user/check/zwy123/1?r=0.8240310607943684&callback=jsonp1507864301740&_=1507864308034
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public Object check(String callback,@PathVariable String param,@PathVariable Integer type){
		try {
			Boolean b = userService.check(param,type);
			MappingJacksonValue MJV = new MappingJacksonValue(SysResult.oK(b));
			MJV.setJsonpFunction(callback);
			return MJV;
		} catch (Exception e) {
			return SysResult.build(201, "校验信息时出错");
		}
	}
	@RequestMapping("/user/register")
	@ResponseBody
	public SysResult goRegister(User user){
		try {
			userService.goRegister(user);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, "注册失败");
		}
	}
	@RequestMapping("/user/login")
	@ResponseBody
	public SysResult login(String u,String p){
		try {
			String ticket=userService.login(u,p);
			return SysResult.oK(ticket);			
		} catch (Exception e) {
			return SysResult.build(201, "用户登录失败");
		}
	}
	/*
	 * 用来响应前台cookie发来的票根查询请求
	 * 这是个jsonp的跨域调用 需要接收callback参数
	 * 当controller拿到ticket之后 会去redis里查对应的value(用户信息的json)
	 * 
	 */
	@RequestMapping("/user/query/{ticket}")
	@ResponseBody
	public Object queryByTicket(String callback,@PathVariable String ticket){
		String json = jedisCluster.get(ticket);
		MappingJacksonValue MJV = new MappingJacksonValue(SysResult.oK(json));
		MJV.setJsonpFunction(callback);
		return MJV;
	}
}
