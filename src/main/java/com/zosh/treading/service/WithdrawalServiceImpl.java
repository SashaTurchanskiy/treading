package com.zosh.treading.service;

import com.zosh.treading.domain.WithdrawalStatus;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Withdrawal;
import com.zosh.treading.repository.WithdrawalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService{

    private final WithdrawalRepo withdrawalRepo;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal processWithdrawal(Long withdrawalId, boolean accept) {
        return null;
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        return List.of();
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequests() {
        return List.of();
    }
}
