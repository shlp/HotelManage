package com.ssm.hotel.dao;

import com.ssm.hotel.entity.ChangeRoom;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChangeRoomDao {
    int addChange(ChangeRoom changeRoom);
    List<ChangeRoom> findList(Map<String, Object> queryMap);
    Integer getTotal(Long id);
    ChangeRoom find(Long id);
    Integer getTotalByType(Map<String, Object> queryMap);
    List<ChangeRoom> findListByCheck(Long checkId);
    List<Map> getStatsInChange();
}
