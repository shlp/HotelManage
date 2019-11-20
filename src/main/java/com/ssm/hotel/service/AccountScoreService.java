package com.ssm.hotel.service;

import com.ssm.hotel.entity.Account;
import com.ssm.hotel.entity.AccountScore;
import org.springframework.stereotype.Service;

@Service
public interface AccountScoreService {
    int add(AccountScore accountScore);
    AccountScore findByAccountId(Long id);
    int edit(AccountScore accountScore);
}
