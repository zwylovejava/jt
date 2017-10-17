package com.jt.manage.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	//引入日志根据；类
	private static Logger logger = Logger.getLogger(ItemController.class);
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Item> findItemList(){
		return itemService.findItemList();
	}
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
/*	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemPageList(int page,int rows){

		return itemService.findItemPageList(page, rows);
	}*/
	
/*	@RequestMapping("/cat/queryItemName")
	public void findItemCatName(Long itemId,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		try {
			//根据商品查询商品名称
			String name = itemService.findItemCatNameItemId(itemId);
			//通过reponse对象实现参数的返回
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatName(Long itemId){
	    //根据商品查询商品名称
	   String name = itemService.findItemCatNameItemId(itemId);
	   return name;
	}
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemPageList(int page,int rows){
		//为分页插件赋值,指定页数和每页展现的记录总数
		PageHelper.startPage(page, rows);		
		//2必须在设定参数之后进行商品的查询全部记录数的查询,否则分页插件将不能进行
		//3因为分页插件会自动调用该方法,所以查询的记录的最终结果就是分页的结果数据
		List<Item> itemList = itemService.findItemList();
		//System.out.println(itemList.size());
		//通过分页的Info会自己计算分页的记录数和总数
		PageInfo<Item> info  = new PageInfo<Item>(itemList);				
		return new EasyUIResult(info.getTotal(), itemList);
	
	}
	//测试通用mapper查询全部记录数
	@RequestMapping("/findCount")
	@ResponseBody
	public int findMapperCount(){
		
		
		return itemService.findMapperCount();
	}
	//http://localhost:8091/item/save   
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){		
		try {
			//为了满足事务的一致性需要在业务层处理
			itemService.saveItem(item,desc);
			//记录日志信息
			logger.info("新增商品成功");
			return new SysResult(200,"新增商品成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new SysResult(201,"新增商品失败",null);
		}
		
	}
	//item/update
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		
		try {			
			itemService.updateItem(item,desc);
			//记录日志信息
			logger.info("修改商品成功");
			return new SysResult(200,"修改商品成功",null);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new SysResult(201,"修改商品失败",null);
		}
		
		
	}
	//item/delete
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(String[] ids){
		
		try {			
			itemService.deleteItem(ids);
			//记录日志信息
			logger.info("删除商品成功");
			return new SysResult(200,"删除商品成功",null);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new SysResult(201,"删除商品失败",null);
		}				
	}
	//item/instock
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instockItem(Long[] ids){
		
		try {	
			int status=2;//下架
			itemService.instockItem(ids,status);
			//记录日志信息
			logger.info("下架成功");
			return new SysResult(200,"下架成功",null);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new SysResult(201,"下架失败",null);
		}				
	}
	//item/reshelf
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelfItem(Long[] ids){		
		try {	
			int status=1;//下架
			itemService.instockItem(ids,status);
			//记录日志信息
			logger.info("上架成功");
			return new SysResult(200,"上架成功",null);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new SysResult(201,"上架失败",null);
		}				
	}
	//实现商品信息的回显操作/item/query/item/desc/'+data.id
	
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult queryDesc(@PathVariable Long itemId){
		try {
			ItemDesc itemDesc= itemService.queryDesc(itemId);
			logger.info("商品描述查询成功");
			return SysResult.build(200, "商品描述查询成功", itemDesc);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return SysResult.build(201, "商品，描述查询失败");
		}
		
		
	}
	
	
}
