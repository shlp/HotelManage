package com.ssm.hotel.service;

import com.ssm.hotel.entity.Floor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 楼层service
 * @author Administrator
 *
 */
@Service
public interface FloorService {
	 int add(Floor floor);
	 int edit(Floor floor);
	 int delete(Long id);
	 List<Floor> findList(Map<String, Object> queryMap);
	 List<Floor> findAll();
	 Integer getTotal(Map<String, Object> queryMap);
}
