package com.ssm.hotel.service;

import com.ssm.hotel.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色role service
 * @author llq
 *
 */
@Service
public interface RoleService {
	 int add(Role role);
	 int edit(Role role);
	 int delete(Long id);
	 List<Role> findList(Map<String, Object> queryMap);
	 int getTotal(Map<String, Object> queryMap);
	 Role find(Long id);
}
