package com.ssm.hotel.service.impl;


import com.ssm.hotel.dao.RoomDao;
import com.ssm.hotel.entity.Room;
import com.ssm.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDao roomDao;
	
	@Override
	public int add(Room room) {
		// TODO Auto-generated method stub
		return roomDao.add(room);
	}

	@Override
	public int edit(Room room) {
		// TODO Auto-generated method stub
		return roomDao.edit(room);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return roomDao.delete(id);
	}

	@Override
	public List<Room> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomDao.findList(queryMap);
	}

	@Override
	public List<Room> findAll() {
		// TODO Auto-generated method stub
		return roomDao.findAll();
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return roomDao.getTotal(queryMap);
	}

	@Override
	public Room find(Long id) {
		// TODO Auto-generated method stub
		return roomDao.find(id);
	}

	@Override
	public Room findBySn(String sn) {
		// TODO Auto-generated method stub
		return roomDao.findBySn(sn);
	}

	@Override
	public int swapRoom(Long id) {
		return roomDao.swapRoom(id);
	}

	@Override
	public int swapok(Long id) {
		return roomDao.swapok(id);
	}

	@Override
	public int swapquick(Long id) {
		return roomDao.swapquick(id);
	}

	@Override
	public int updateRoom(Long id) {
		return roomDao.updateRoom(id);
	}

	@Override
	public int checkRoom(Room room){ return roomDao.checkRoom(room);}

	@Override
	public List<Room> findListByIds(String ids){return roomDao.findListByIds(ids);}

	@Override
	public Integer findTotalListByIds(String ids){return roomDao.findTotalListByIds(ids);}

	@Override
	public List<Room> findListByRoomIds(String ids){return roomDao.findListByRoomIds(ids);}

	@Override
	public Integer findTotalListByRoomIds(String ids){return roomDao.findTotalListByRoomIds(ids);}
}

