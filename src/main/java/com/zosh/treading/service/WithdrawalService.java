package com.zosh.treading.service;

import com.zosh.treading.model.User;
import com.zosh.treading.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);
    Withdrawal processWithdrawal(Long withdrawalId, boolean accept) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequests();
}
