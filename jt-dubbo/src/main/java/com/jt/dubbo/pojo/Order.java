package com.jt.dubbo.pojo;

import java.util.Date;
import java.util.List;

import com.jt.common.po.BasePojo;

/**
 * 对应 tb_order
 * @author Administrator
 *
 */
//需要关联查询
public class Order extends BasePojo {
/*
 *   order_id             varchar(50) not null comment '订单号：登录用户id+当前时间戳',
   payment              varchar(50) comment '精确到2位小数；单位：元。如：200.09，表示：200元7分。',
   payment_type         int(2) comment '1、在线支付，2、货到付款',
   post_fee             varchar(50) comment '邮费。精确到2位小数；单位：元。如：200.09',
   status               int comment '状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭',
   payment_time         datetime,
   consign_time         datetime,
   end_time             datetime,
   close_time           datetime,
   shipping_name        varchar(20),
   shipping_code        varchar(20),
   user_id              bigint(20),
   buyer_message        varchar(100),
   buyer_nick           varchar(50),
   buyer_rate           int(2),
   created              datetime,
   updated              datetime,
 */
	//表示和订单物流表一对一关系
	private OrderShipping orderShipping; 
	private List<OrderItem> OrderItems;
	private String orderId;
	private String payment;
	private Integer status;
	private Integer paymentType;
	private String postFee;
	private Date paymentTime;
	private Date consignTime;
	private Date endTime; 
	private Date closeTime; 
	private String  shippingName; 
	private String  shippingCode; 
	private Long  userId; 
	private String  buyerMessage; 
	private String  buyerNick; 
	private Integer  buyerRate;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public Integer getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	public List<OrderItem> getOrderItems() {
		return OrderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		OrderItems = orderItems;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	} 
	 
	
	
}
