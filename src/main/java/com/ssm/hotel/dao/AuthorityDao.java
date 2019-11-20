package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限实现类dao
 * @author llq
 *
 */
@Repository
public interface AuthorityDao {
	 int add(Authority authority);
	 int deleteByRoleId(Long roleId);
	 List<Authority> findListByRoleId(Long roleId);
}
