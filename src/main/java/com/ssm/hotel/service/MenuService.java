package com.ssm.hotel.service;

import com.ssm.hotel.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理service
 * @author llq
 *
 */
@Service
public interface MenuService {
	 int add(Menu menu);
	 List<Menu> findList(Map<String, Object> queryMap);
	 List<Menu> findTopList();
	 int getTotal(Map<String, Object> queryMap);
	 int edit(Menu menu);
	 int delete(Long id);
	 List<Menu> findChildernList(Long parentId);
	 List<Menu> findListByIds(String ids);
}
