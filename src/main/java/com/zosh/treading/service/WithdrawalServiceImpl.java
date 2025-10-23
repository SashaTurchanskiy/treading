package com.zosh.treading.service;

import com.zosh.treading.domain.WithdrawalStatus;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Withdrawal;
import com.zosh.treading.repository.WithdrawalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Withdrawal processWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepo.findById(withdrawalId);
        if (withdrawal.isEmpty()){
            throw new Exception("Withdrawal request not found");
        }
        Withdrawal w1 = withdrawal.get();
        w1.setDate(LocalDateTime.now());
        if (accept){
            w1.setStatus(WithdrawalStatus.SUCCESS);
        }else {
            w1.setStatus(WithdrawalStatus.PENDING);
        }
        return withdrawalRepo.save(w1);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(User user) {
        return withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequests() {
        return withdrawalRepo.findAll();
    }
}
