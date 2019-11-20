package com.ssm.hotel.service;

import com.ssm.hotel.entity.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限service接口
 * @author llq
 *
 */
@Service
public interface AuthorityService {
	 int add(Authority authority);
	 int deleteByRoleId(Long roleId);
	 List<Authority> findListByRoleId(Long roleId);
}
