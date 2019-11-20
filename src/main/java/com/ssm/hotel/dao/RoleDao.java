package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色role dao
 * @author llq
 *
 */
@Repository
public interface RoleDao {
	 int add(Role role);
	 int edit(Role role);
	 int delete(Long id);
	 List<Role> findList(Map<String, Object> queryMap);
	 int getTotal(Map<String, Object> queryMap);
	 Role find(Long id);
}
