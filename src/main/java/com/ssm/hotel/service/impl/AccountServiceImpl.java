package com.ssm.hotel.service.impl;

import com.ssm.hotel.dao.AccountDao;
import com.ssm.hotel.entity.Account;
import com.ssm.hotel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public int add(Account account) {
        return accountDao.add(account);
    }

    @Override
    public int reg(Account account) {
        return accountDao.reg(account);
    }

    @Override
    public int edit(Account account) {
        return accountDao.edit(account);
    }

    @Override
    public int editPassword(Account account) {
        return accountDao.editPassword(account);
    }

    @Override
    public int delete(Long id) {
        return accountDao.delete(id);
    }

    @Override
    public List<Account> findList(Map<String, Object> queryMap) {
        return accountDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return accountDao.getTotal(queryMap);
    }

    @Override
    public Account find(Long id) {
        return accountDao.find(id);
    }

    @Override
    public Account findByName(String name) {
        return accountDao.findByName(name);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}
