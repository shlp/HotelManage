package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Floor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 楼层dao
 * @author Administrator
 *
 */
@Repository
public interface FloorDao {
	 int add(Floor floor);
	 int edit(Floor floor);
	 int delete(Long id);
	 List<Floor> findList(Map<String, Object> queryMap);
	 List<Floor> findAll();
	 Integer getTotal(Map<String, Object> queryMap);
}
