package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;
//当前接口继承了SysMapper可以对单表进行curd操作
public interface ItemMapper extends SysMapper<Item> {
	public List<Item> findItemList();

	public List<Item> findItemPageList(@Param("start")int start, @Param("rows")int rows);

	public String findItemCatNameItemId(Long itemId);

	public void instockItem(@Param("ids")Long[] ids, @Param("status")int status);
}
