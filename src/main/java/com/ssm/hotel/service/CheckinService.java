package com.ssm.hotel.service;
/**
 * 入住service
 */

import com.ssm.hotel.entity.Checkin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CheckinService {
	 int add(Checkin checkin);
	 int edit(Checkin checkin);
	 int delete(Long id);
	 List<Checkin> findList(Map<String, Object> queryMap);
	 Integer getTotal(Map<String, Object> queryMap);
	 Checkin find(Long id);
	Integer getTotalByType(Map<String, Object> queryMap);
	Integer getTotalByTypeNull(Map<String, Object> queryMap);
	int updateByNo(Long id);
	 int continueLive(Checkin checkin);

}
