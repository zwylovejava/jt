package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
	/**
	 * 这是common提供的redis工具类
	 * 底层是通过jides来实现de
	 */
	
	@Autowired	
	private RedisService redisService;	
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 实现思路
	 * 商品类目查询:引入缓存
	 * 首次查询时，因为缓存里没有数据,所以去数据库可查询
	 * 将数据查出,返回给前台并将数据存在缓存里
	 * 缓存里的数据形式:k-v k是商品分类id v是List<ItemCat>的json串
	 * 
	 * 当从缓存里取数据的时候  通过k取去json串,再转成List<ItemCat>传给前台
	 * 
	 */
	@Override
	public List<ItemCat> findItemCatList(Long parentId) {
		String ITEM_CAT_KEY = "ITEM_CAT_"+parentId;
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
		
	}

	@Override
	public ItemCatResult jsonp() {
		//先查询出所有商品分类信息
		List<ItemCat> LIST_ALL= itemCatMapper.select(null);
		
		List<ItemCatData> LEVEL_1=new ArrayList<>();

		for (ItemCat L1 : LIST_ALL) {
			//筛选一级分类商品数据			
			if(L1.getParentId()==0){
				ItemCatData ICD_1 = new ItemCatData();
				ICD_1.setUrl("/products/"+L1.getId()+".html");
				ICD_1.setName("<a href='/products/"+L1.getId()+".html'>"+L1.getName()+"</a>");
				List<ItemCatData> LEVEL_2=new ArrayList<>();
				
				List<ItemCat> L2_LIST=itemCatMapper.findItemCatByParentId(L1.getId());
				for(ItemCat L2:L2_LIST){
					//[]
					ItemCatData ICD_2=new ItemCatData();
					ICD_2.setUrl("/products/"+L2.getId()+".html");
					ICD_2.setName(L2.getName());
					List<String> LEVEL_3 = new ArrayList<>();
					
					ICD_2.setItems(LEVEL_3);
					LEVEL_2.add(ICD_2);
					List<ItemCat> L3_LIST=itemCatMapper.findItemCatByParentId(L2.getId());
					
					for (ItemCat L3 : L3_LIST) {
						LEVEL_3.add("/products/"+L3.getId()+".html|"+L3.getName());
						
					}
					
				}
				ICD_1.setItems(LEVEL_2);
				LEVEL_1.add(ICD_1);	
			}
		}
		
		
		ItemCatResult result = new ItemCatResult();
		result.setItemCats(LEVEL_1);
		return result;
	}

}
