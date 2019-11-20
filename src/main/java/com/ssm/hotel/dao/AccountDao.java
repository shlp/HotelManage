package com.ssm.hotel.dao;

import com.ssm.hotel.entity.Account;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
用户Dao
 */
@Repository
public interface AccountDao {
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
