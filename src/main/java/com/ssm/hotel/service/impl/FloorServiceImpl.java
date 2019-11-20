package com.ssm.hotel.service.impl;
/**
 * 楼层Service
 */

import com.ssm.hotel.dao.FloorDao;
import com.ssm.hotel.entity.Floor;
import com.ssm.hotel.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FloorServiceImpl implements FloorService {

	@Autowired
	private FloorDao floorDao;
	
	@Override
	public int add(Floor floor) {
		// TODO Auto-generated method stub
		return floorDao.add(floor);
	}

	@Override
	public int edit(Floor floor) {
		// TODO Auto-generated method stub
		return floorDao.edit(floor);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return floorDao.delete(id);
	}

	@Override
	public List<Floor> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return floorDao.findList(queryMap);
	}

	@Override
	public List<Floor> findAll() {
		// TODO Auto-generated method stub
		return floorDao.findAll();
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return floorDao.getTotal(queryMap);
	}

}
