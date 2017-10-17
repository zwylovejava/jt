package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

public interface ItemService {
	public List<Item> findItemList();
	
	public EasyUIResult findItemPageList(int page,int rows);

	public String findItemCatNameItemId(Long itemId);

	public int findMapperCount();

	public void saveItem(Item item,String desc);
	//修改商品
	public void updateItem(Item item,String desc);

	public void deleteItem(String[] ids);
	//根据商品ID上架商品
	public void instockItem(Long[] ids, int status);

	public ItemDesc queryDesc(Long itemId);

	public Item findItemById(Long itemId);

	

	
}
