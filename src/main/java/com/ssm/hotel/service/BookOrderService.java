package com.ssm.hotel.service;
/**
 * 预订信息service
 */

import com.ssm.hotel.entity.BookOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookOrderService {
	 int add(BookOrder bookOrder);
	 int edit(BookOrder bookOrder);
	 int delete(BookOrder bookOrder);
	 List<BookOrder> findList(Map<String, Object> queryMap);
	 Integer getTotal(Map<String, Object> queryMap);
	 BookOrder find(Long id);
	 Integer getTotalByType(Map<String, Object> queryMap);
}
