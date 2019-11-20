package com.ssm.hotel.dao;

import com.ssm.hotel.entity.RoomType;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface RoomTypeDao {
     int add(RoomType roomType);
     int edit(RoomType roomType);
     int delete(Long id);
     List<RoomType> findList(Map<String, Object> queryMap);
     Integer getTotal(Map<String, Object> queryMap);
     List<RoomType> findAll();
     RoomType find(Long id);
     int updateNum(RoomType roomType);
     int updateStatus(Long id);
     int updateAvilableNum(RoomType roomType);
     List<RoomType> findListByIds(String ids);
}
