package com.ssm.hotel.service.impl;

import com.ssm.hotel.dao.DetailsDao;
import com.ssm.hotel.entity.Details;
import com.ssm.hotel.service.DetailsService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    private DetailsDao detailsDao;

    @Override
    public int add(Details details) {
        return detailsDao.add(details);
    }

    @Override
    public int addByNoChange(Details details) {
        return detailsDao.addByNoChange(details);
    }

    @Override
    public List<Map> getStatsByMonth() {
        return detailsDao.getStatsByMonth();
    }

    @Override
    public List<Map> getStatsInCheck() {
        return detailsDao.getStatsInCheck();
    }

    @Override
    public List<Details> findList(Map<String, Object> queryMap) {
        return detailsDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return detailsDao.getTotal(queryMap);
    }

    @Override
    public List<Details> findAll() {
        return detailsDao.findAll();
    }

    @Override
    public Integer getTotalAll() {
        return detailsDao.getTotalAll();
    }
}
