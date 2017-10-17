package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.pojo.ItemCat;

public interface ItemCatService {

	List<ItemCat> findItemCatList(Long parentId);

	ItemCatResult jsonp();

}
