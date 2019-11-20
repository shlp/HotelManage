package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Checkin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 入住dao
 */

@Repository
public interface CheckinDao {
	 int add(Checkin checkin);
	 int edit(Checkin checkin);
	 int delete(Long id);
	 List<Checkin> findList(Map<String, Object> queryMap);
	 Integer getTotal(Map<String, Object> queryMap);
	 Checkin find(Long id);
	 Integer getTotalByType(Map<String, Object> queryMap);
	 Integer getTotalByTypeNull(Map<String, Object> queryMap);
	 int continueLive(Checkin checkin);
	int updateByNo(Long id);

}
