package com.ssm.hotel.service.impl;

import com.ssm.hotel.dao.AccountScoreDao;
import com.ssm.hotel.entity.AccountScore;
import com.ssm.hotel.service.AccountScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountScoreServiceImpl implements AccountScoreService {

    @Autowired
    private AccountScoreDao accountScoreDao;

    @Override
    public int add(AccountScore accountScore){return  accountScoreDao.add(accountScore);}
    @Override
    public AccountScore findByAccountId(Long id){return accountScoreDao.findByAccountId(id);}
    @Override
    public int edit(AccountScore accountScore){return accountScoreDao.edit(accountScore);}
}
