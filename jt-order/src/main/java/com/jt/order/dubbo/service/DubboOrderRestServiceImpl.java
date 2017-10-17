package com.jt.order.dubbo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.service.DubboOrderRestService;
import com.jt.order.mapper.OrderMapper;

public class DubboOrderRestServiceImpl implements DubboOrderRestService {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private OrderMapper orderMapper;
	@Override
	public String createOrder(String json) {
		try {
			Order order =MAPPER.readValue(json, Order.class);
			//生成订单ID
			String orderId = order.getUserId()+""+System.currentTimeMillis();
			order.setOrderId(orderId);
			//向数据库里插入orderd对象的数据  这里会用到  用到MyBatis的批量新增
			//向tb_order,tb_order_shipping tb_order_item
		
			order.setCreated(new Date());
			order.setUpdated(order.getCreated());
			orderMapper.createOrder(order);
			return orderId;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
}
