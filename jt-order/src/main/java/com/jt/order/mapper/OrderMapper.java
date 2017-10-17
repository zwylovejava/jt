package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Order;

public interface OrderMapper extends SysMapper<Order> {

	void createOrder(Order order);

}
