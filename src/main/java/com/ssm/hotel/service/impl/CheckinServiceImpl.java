package com.ssm.hotel.service.impl;
/**
 * ��ס����s入住信息servic
 */


import com.ssm.hotel.dao.CheckinDao;
import com.ssm.hotel.entity.Checkin;
import com.ssm.hotel.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CheckinServiceImpl implements CheckinService {

	@Autowired
	private CheckinDao checkinDao;

	@Override
	public int add(Checkin checkin) {
		// TODO Auto-generated method stub
		return checkinDao.add(checkin);
	}

	@Override
	public int edit(Checkin checkin) {
		// TODO Auto-generated method stub
		return checkinDao.edit(checkin);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return checkinDao.delete(id);
	}

	@Override
	public List<Checkin> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return checkinDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return checkinDao.getTotal(queryMap);
	}

	@Override
	public Checkin find(Long id) {
		// TODO Auto-generated method stub
		return checkinDao.find(id);
	}

	@Override
	public Integer getTotalByType(Map<String, Object> queryMap) {
		return checkinDao.getTotalByType(queryMap);
	}
	@Override
	public Integer getTotalByTypeNull(Map<String, Object> queryMap) {
		return checkinDao.getTotalByTypeNull(queryMap);
	}

	@Override
	public 	int continueLive(Checkin checkin){return checkinDao.continueLive(checkin);}
	
	@Override
	public int updateByNo(Long id){return checkinDao.updateByNo(id);}
	
	
	

}
