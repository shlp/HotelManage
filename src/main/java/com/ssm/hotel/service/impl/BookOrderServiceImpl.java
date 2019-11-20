package com.ssm.hotel.service.impl;


import com.ssm.hotel.dao.BookOrderDao;
import com.ssm.hotel.entity.BookOrder;
import com.ssm.hotel.service.BookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookOrderServiceImpl implements BookOrderService {

	@Autowired
	private BookOrderDao bookOrderDao;

	@Override
	public int add(BookOrder bookOrder) {
		// TODO Auto-generated method stub
		return bookOrderDao.add(bookOrder);
	}

	@Override
	public int edit(BookOrder bookOrder) {
		// TODO Auto-generated method stub
		return bookOrderDao.edit(bookOrder);
	}

	@Override
	public int delete(BookOrder bookOrder) {
		// TODO Auto-generated method stub
		return bookOrderDao.delete(bookOrder);
	}

	@Override
	public List<BookOrder> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return bookOrderDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return bookOrderDao.getTotal(queryMap);
	}

	@Override
	public BookOrder find(Long id) {
		// TODO Auto-generated method stub
		return bookOrderDao.find(id);
	}

	@Override
	public Integer getTotalByType(Map<String, Object> queryMap){
		return bookOrderDao.getTotalByType(queryMap);
	}

	
	
	

}
