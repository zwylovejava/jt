package com.jt.cart.dubbo.service;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.cart.mapper.CartMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartRestService;
/**
 * 当provider 提供服务后,消费的时候报错 check provider if start
 * 处理办法 停止dubbo(停止dubbo控制台),进入zookeeper的客户端,删除/dubbo节点 rmr /duubo(递归删除)-->启动dubbo-->启动购物车项目-->启动前台项目
 * @author Administrator
 *
 */
public class DubboCartRestServiceImpl implements DubboCartRestService {
	@Autowired
	private CartMapper cartMapper;
	//根据传来的用户ID查询出List<Cart>返回
	@Override
	public List<Cart> myCartList(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		List<Cart> cartList = cartMapper.select(cart);
		return cartList;
	}
	//实现购物车商品的保存
	//select * from tb_cart where userId=#{userId} and itemId=#{itemId}
	//先要根据用户ID和商品ID判断该用户是否存在此商品
	//如果存在做商品数量的累加
	//如果不存在做商品的新增
	@Override
	public void saveCart(Cart cart) {
		int count = cartMapper.selectCount(cart);
		if(count==1){
			//说明此商品已存在
			Cart db_cart = cartMapper.select(cart).get(0);
			db_cart.setNum(db_cart.getNum()+cart.getNum());
			db_cart.setUpdated(new Date());
			cartMapper.updateByPrimaryKey(db_cart);
		}else{
			//说明此商品时新商品
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
		
	}
	/*
	 * 实现购物车商品数量的更新
	 * update tb_cart set num=#{num},updated=#{updated} where userId=#{userId} and itemId=#{itemId}
	 */
	@Override
	public void updateNum(Long userId, Integer num, Long itemId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		//自定义的更新购物车数量的方法
		cartMapper.updateCart(cart);
	}
	/*
	 * 根据用户id和商品id删除购物车商品
	 * 
	 */
	@Override
	public void delete(Long userId, Long itemId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartMapper.delete(cart);
		
	}
}
