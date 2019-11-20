package com.ssm.hotel.service;

import com.ssm.hotel.entity.Room;
import com.ssm.hotel.entity.RoomType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoomTypeService {
     int add(RoomType roomType);
     int edit(RoomType roomType);
     int delete(Long id);
     List<RoomType> findList(Map<String, Object> queryMap);
     List<RoomType> findAll();
     Integer getTotal(Map<String, Object> queryMap);
     RoomType find(Long id);
     int updateNum(RoomType roomType);
     int updateStatus(Long id);
     int updateAvilableNum(RoomType roomType);
     List<RoomType> findListByIds(String ids);
}

