package com.ssm.hotel.service.impl;

import com.ssm.hotel.dao.ChangeRoomDao;
import com.ssm.hotel.entity.ChangeRoom;
import com.ssm.hotel.service.ChangeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChangeRoomServiceImpl implements ChangeRoomService {

    @Autowired
    private ChangeRoomDao changeRoomDao;

    @Override
    public int addChange(ChangeRoom changeRoom){
        return  changeRoomDao.addChange(changeRoom);
    }

    @Override
    public List<ChangeRoom> findList(Map<String, Object> queryMap) {
        return changeRoomDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Long id) {
        return changeRoomDao.getTotal(id);
    }

    @Override
    public ChangeRoom find(Long id) {
        return changeRoomDao.find(id);
    }

    @Override
    public Integer getTotalByType(Map<String, Object> queryMap) {
        return changeRoomDao.getTotalByType(queryMap);
    }

    @Override
    public List<ChangeRoom> findListByCheck(Long checkId){
        return changeRoomDao.findListByCheck(checkId);
    }
    @Override
    public List<Map> getStatsInChange(){return changeRoomDao.getStatsInChange();}
}
