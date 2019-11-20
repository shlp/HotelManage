package com.ssm.hotel.service;

import com.ssm.hotel.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AccountService {
    int add(Account account);
    int edit(Account account);
    int editPassword(Account account);
    int delete(Long id);
    List<Account> findList(Map<String, Object> queryMap);
    Integer getTotal(Map<String, Object> queryMap);
    Account find(Long id);
    Account findByName(String name);
    List<Account> findAll();
    int reg(Account account);
}
