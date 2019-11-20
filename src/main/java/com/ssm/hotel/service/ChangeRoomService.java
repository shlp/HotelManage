package com.ssm.hotel.service;

import com.ssm.hotel.entity.ChangeRoom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ChangeRoomService {
    int addChange(ChangeRoom changeRoom);
    List<ChangeRoom> findList(Map<String, Object> queryMap);
    Integer getTotal(Long id);
    ChangeRoom find(Long id);
    Integer getTotalByType(Map<String, Object> queryMap);
    List<ChangeRoom> findListByCheck(Long checkId);
    List<Map> getStatsInChange();
}
