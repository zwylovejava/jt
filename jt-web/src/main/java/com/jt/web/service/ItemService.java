package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
/**
 * 用于商品前台详情展示的
 * 在这个类里,会向后台发起HttpClient,并接受后台传来的Item的json
 * @author Administrator
 *
 */
@Service
public class ItemService {
	@Autowired
	private HttpClientService httpClient;
	private static final ObjectMapper MAPPER=new ObjectMapper();
	public Item findItemById(Long itemId) {
		try {
			//向后台发起请求,将商品id传给后台,然后通过id去数据库查询
			//返回的是 item的json串
			String json = httpClient.doGet("http://manage.jt.com/web/item/"+itemId);
			//这里注意,Item是前台
			Item item = MAPPER.readValue(json, Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
