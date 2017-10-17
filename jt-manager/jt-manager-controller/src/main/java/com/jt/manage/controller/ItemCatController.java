package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	
//http://localhost:8091/item/cat/list
	/**
	 * {"id":2,"text":"商品名",state:"closed"}
		注:state的属性如果是closed，表示这个是父节点，它还有子节点。open代表子节点
		3根据父节点ID查询子节点的ID
		
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<ItemCat> findItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		return itemCatService.findItemCatList(parentId);
	}
	
}
