package com.ssm.hotel.service;
/**
 * 房间service
 */

import com.ssm.hotel.entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoomService {
	 int add(Room room);
	 int edit(Room room);
	 int delete(Long id);
	 List<Room> findList(Map<String, Object> queryMap);
	 List<Room> findAll();
	 Integer getTotal(Map<String, Object> queryMap);
	 Room find(Long id);
	 Room findBySn(String sn);
	int swapRoom(Long id);
	int swapok(Long id);
	int swapquick(Long id);
	int updateRoom(Long id);
	int checkRoom(Room room);
	List<Room> findListByIds(String ids);
	Integer findTotalListByIds(String ids);
	List<Room> findListByRoomIds(String ids);
	Integer findTotalListByRoomIds(String ids);
}
