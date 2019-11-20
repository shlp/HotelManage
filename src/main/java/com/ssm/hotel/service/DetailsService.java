package com.ssm.hotel.service;

import com.ssm.hotel.entity.Details;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DetailsService {
    int add(Details details);
    int addByNoChange(Details details);
    List<Map> getStatsByMonth();
    List<Map> getStatsInCheck();
    List<Details> findList(Map<String, Object> queryMap);
    Integer getTotal(Map<String, Object> queryMap);
    List <Details> findAll();
    Integer getTotalAll();

}
