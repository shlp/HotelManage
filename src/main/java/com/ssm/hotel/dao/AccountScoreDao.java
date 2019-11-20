package com.ssm.hotel.dao;

import com.ssm.hotel.entity.AccountScore;
import com.ssm.hotel.service.AccountScoreService;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountScoreDao {

    int add(AccountScore accountScore);
    AccountScore findByAccountId(Long id);
    int edit(AccountScore accountScore);
}
