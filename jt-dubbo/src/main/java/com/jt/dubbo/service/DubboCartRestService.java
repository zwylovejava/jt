package com.jt.dubbo.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jt.dubbo.pojo.Cart;
@Path("cart")
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
public interface DubboCartRestService {
	@GET
	@Path("query")
	public List<Cart> myCartList(@QueryParam(value="userId")Long userId);
	@POST
	@Path("save")
	public void saveCart(Cart cart);
	@GET
	@Path("update/num")
	public void updateNum(@QueryParam(value="userId")Long userId, @QueryParam(value="num")Integer num, @QueryParam(value="itemId")Long itemId);
	@GET
	@Path("delete")
	public void delete(@QueryParam(value="userId")Long userId, @QueryParam(value="itemId")Long itemId);
}
