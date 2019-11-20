package com.ssm.hotel.service;

import com.ssm.hotel.entity.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 日志接口
 * @author llq
 *
 */
@Service
public interface LogService {
	 int add(Log log);
	 int add(String content);
	 List<Log> findList(Map<String, Object> queryMap);
	 int getTotal(Map<String, Object> queryMap);
	 int delete(String ids);
}
