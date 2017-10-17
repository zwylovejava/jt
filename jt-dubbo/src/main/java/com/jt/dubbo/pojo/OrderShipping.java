package com.jt.dubbo.pojo;

import com.jt.common.po.BasePojo;

/**
 * 对应 tb_order_shipping 订单物流表和订单物流表
 * @author Administrator
 *
 */
public class OrderShipping extends BasePojo {
/*
 * create table tb_order_shipping
(
   order_id             varchar(50) not null,
   receiver_name        varchar(20),
   receiver_phone       varchar(20),
   receiver_mobile      varchar(30),
   receiver_state       varchar(10),
   receiver_city        varchar(10),
   receiver_district    varchar(20),
   receiver_address     varchar(200),
   receiver_zip         varchar(6),
   created              datetime,
   updated              datetime,
   primary key (order_id)
);
 */
	private String orderId;
	private String receiverName;
	private String receiverPhone;
	private String receiverMobile;
	private String receiverState;
	private String receiverCity;
	private String receiverDistrict;
	private String receiverAddress;
	private String receiverZip;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
	
}
