package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.pojo.ItemDesc;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemServcieImpl implements ItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	/**
	 * 这是common提供的redis工具类
	 * 底层是通过jides来实现de
	 */	
	@Autowired	
	private RedisService redisService;	
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	
	
	@Override
	public List<Item> findItemList() {
		
		return itemMapper.findItemList();
	}
	/**
	 * 分页的业务逻辑
	 * {"total":2000,"rows":[{},{},{}]}
		total 是记录总数
		rows表示查询的数据
	 * 
	 */
	
	@Override
	public EasyUIResult findItemPageList(int page, int rows) {
		//使通用mapper查询
		int count = itemMapper.selectCount(null);
		
		int start = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemPageList(start,rows);
		
		return new EasyUIResult(count,itemList) ;
	}
	@Override
	public String findItemCatNameItemId(Long itemId) {
		
		
		return itemMapper.findItemCatNameItemId(itemId);
	}
	@Override
	public int findMapperCount() {
		//itemMapper调用通用Mapper的语句
		//com.jt.common.mapper.itemMapper.findMapperCount()
		return itemMapper.findMapperCount();
	}
	/**
	 * 问题分析；
	 * 当我新增入库时 由于数据库是主键自增,所以在新增之后才能获取ID
	 * 当我新增ItemDesc时必须有ID否则新增失败
	 * 2决绝办法、
	 * 让ItemId先入库操作 才能获取ID
	 * 再次查询最大值 为ItemDesc赋值 这样的方式不行  在高并发的条件下有问题
	 * 
	 * 3将Item对象整个当成where条件 在一定条件下可以
	 * 
	 * 
	 * 
	 * 4正解
	 * 采用JPA的形式操作数据库后 会自动将数据再次回显
	 * 
	 */
	@Override
	public void saveItem(Item item,String desc) {
		item.setStatus(1);//正常
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		//新增主表信息
		itemMapper.insertSelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		
	}
	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDesc.setItemDesc(desc);
		itemMapper.updateByPrimaryKeySelective(item);
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}
	@Override
	public void deleteItem(String[] ids) {
		itemDescMapper.deleteByIDS(ids);
		
		itemMapper.deleteByIDS(ids);
	}
	@Override
	public void instockItem(Long[] ids, int status) {
		itemMapper.instockItem(ids,status);
		
	}
	@Override
	public ItemDesc queryDesc(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
	/**
	 * 		String ITEM_CAT_KEY = "ITEM_CAT_"+parentId;
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		//去缓存里根据key查对应的value值,实际是 List<ItemCat>的json串
		String  resultJson= jedisCluster.get(ITEM_CAT_KEY);
		try {
			
			if(StringUtils.isEmpty(resultJson)){
				List<ItemCat> resultList = itemCatMapper.select(itemCat);
				//将数据存在缓存里
				jedisCluster.set(ITEM_CAT_KEY, objectMapper.writeValueAsString(resultList));
				return resultList;
			}else{
				//进入判断说明缓存里有数据
				List<ItemCat> resultList = 
						objectMapper.readValue(objectMapper.readTree(resultJson).traverse(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, ItemCat.class));
				return resultList;
			}
		} catch (Exception e) {
			//如果缓存出问题需要去数据库查询
			return itemCatMapper.select(itemCat);
		}
	 */
	
	
	
	@Override
	public Item findItemById(Long itemId) {
		String ITEM_KEY = "ITEM_"+itemId;
		//去缓存里根据key查对应的value值,实际是 Item的json串

		try {
			String resultJson = jedisCluster.get(ITEM_KEY);
			if(StringUtils.isEmpty(resultJson)){
							
				Item item= itemMapper.selectByPrimaryKey(itemId);
				String json = objectMapper.writeValueAsString(item);				
				jedisCluster.set(ITEM_KEY, json);
				return item;
			}else{
				//进入判断说明缓存里有数据
				Item item = objectMapper.readValue(resultJson, Item.class);				
				return item;
				
			}
		} catch (Exception e) {
			
			return itemMapper.selectByPrimaryKey(itemId);
		}
	}

}
