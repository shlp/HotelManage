package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Checkin;
import com.ssm.hotel.entity.Details;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DetailsDao {
    int add(Details details);
    int addByNoChange(Details details);
    List<Map> getStatsByMonth();
    List<Map> getStatsInCheck();
    List<Details> findList(Map<String, Object> queryMap);
    Integer getTotal(Map<String, Object> queryMap);
    List <Details> findAll();
    Integer getTotalAll();

}
