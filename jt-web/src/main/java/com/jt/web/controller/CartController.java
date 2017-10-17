package com.jt.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.DubboCartRestService;

@Controller
public class CartController {
	//调用的是Dubbo暴露的购物车接口
	private DubboCartRestService dubboCartRestService;
	/*
	 * 通过这个方法转向购物车页面,页面对应 ：WEB-INF/views/
	 * 通过Model对象,将List<Cart>返回前台,属性名cartList
	 * 购物车属于某个用户的,我们需要根据用户ID去查,今天的业务用户ID写死
	 */
	@RequestMapping("/cart/show")
	public String myCartList(Model model){
		//后期会通过SSO获取登录的用户ID
		Long userId=7L;
		List<Cart> cartList = dubboCartRestService.myCartList(userId);
		model.addAttribute("cartList", cartList);
		//定位到cart.jsp页面
		return "cart";
		
	}
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(Cart cart){
		//后期会通过SSO获取登录的用户ID
		Long userId=7L;
		dubboCartRestService.saveCart(cart);
	
		//定位到cart.jsp页面
		return "cart";
		
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	public String updateDate(@PathVariable Long itemId,@PathVariable Integer num){
		Long userId=7L;
		dubboCartRestService.updateNum(userId,num,itemId);
		return "redirect:/cart/show.html";
	}
	/**
	 * 用来响应购物车商品删除 删除完后 转向cart.jsp
	 * @return
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String delete(@PathVariable Long itemId){
		Long userId=7L;
		dubboCartRestService.delete(userId,itemId);
		return "redirect:/cart/show.html";
	}
	/*
	 * 用来响应购物车订单结算页面的跳转
	 * 此外,需要通过Model,将List<Cart>返回给前台 属性名carts
	 */
	@RequestMapping("/order/create")
	public String cartOrder(Model model){
		Long userId=7L;
		List<Cart> cartList=dubboCartRestService.myCartList(userId);
		model.addAttribute("carts",cartList);
		return "order-cart";
	}
	
}
