package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理dao
 * @author llq
 *
 */
@Repository
public interface MenuDao {
	 int add(Menu menu);
	 List<Menu> findList(Map<String, Object> queryMap);
	 List<Menu> findTopList();
	 int getTotal(Map<String, Object> queryMap);
	 int edit(Menu menu);
	 int delete(Long id);
	 List<Menu> findChildernList(Long parentId);
	 List<Menu> findListByIds(String ids);
}
