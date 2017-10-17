package com.jt.sso.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;

import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServcie extends BaseService<User> {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/*
	 * 根据 param 和type去数据库查询
	 */
	public Boolean check(String param, Integer type) {
		Map<String,String> map = new HashMap<>();
		if(1==type){
			map.put("type", "username");
		}else if(2==type){
			map.put("type", "phone");
		}else{
			map.put("type", "email");
		}
		map.put("param", param);
		//在userMapper.xml里 select count(*) from tb_user where $(type)=#{param}
		int count = userMapper.check(map);
		if(count==0){
			//表示可以注册
			return false;
		}else{
			return true;
		}
		
	}
	public void goRegister(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		userMapper.insert(user);
	}
	/*
	 * 用于做用户登录校验
	 * 实现思路:
	 * 根据u 去数据库 查询,假设得到的对象db_user
	 * 对用户提交的密码进行加密然后和db_user的password进行比对
	 * 校验通过 生成票根(ticket)
	 * 将ticket存到redis缓存里 、
	 * return ticket
	 */
	public String login(String u, String p) {
		User user = new User();
		user.setUsername(u);

		User db_user=userMapper.select(user).get(0);

		if(DigestUtils.md5Hex(p).equals(db_user.getPassword())){
			System.out.println("if");
			//票根的生成算法不唯一 可以自己确定
			String ticket=DigestUtils.md5Hex("TICKET_"+u);
			try {
				jedisCluster.set(ticket, MAPPER.writeValueAsString(db_user));
				return ticket;
			} catch (JsonProcessingException e) {
				System.out.println("12323");
				e.printStackTrace();
				
			}
			
		}
		
		return null;
	}
	
}
