package com.ssm.hotel.dao;

import com.ssm.hotel.entity.BookOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 预定订单dao
 */

@Repository
public interface BookOrderDao {
	 int add(BookOrder bookOrder);
	 int edit(BookOrder bookOrder);
	 int delete(BookOrder bookOrder);
	 List<BookOrder> findList(Map<String, Object> queryMap);
	 Integer getTotal(Map<String, Object> queryMap);
	 BookOrder find(Long id);
	 Integer getTotalByType(Map<String, Object> queryMap);
}
