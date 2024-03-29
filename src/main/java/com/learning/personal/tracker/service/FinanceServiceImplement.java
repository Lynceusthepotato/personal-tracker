package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Finance;
import com.learning.personal.tracker.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinanceServiceImplement implements FinanceService{

    @Autowired
    FinanceRepository financeRepository;

    @Autowired
    TransactionService transactionService;

    @Override
    public Finance getFinanceDetailById(Long userId) throws JSResourceNotFoundException {
        Finance finance = financeRepository.findById(userId);
        finance.setTransaction(transactionService.getAllTransaction(userId));
        return finance;
    }

    @Override
    public Finance addFinance(Long userId, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException {
        Long financeId = financeRepository.create(userId, financeMonthlyBudget, doWarn);
        return financeRepository.findById(userId);
    }

    @Override
    public Finance updateFinance(Long userId, Double financeBudget, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException {
        Finance finance = financeRepository.findById(userId);
        if (finance != null) {
            financeRepository.update(userId, finance.getFinanceId(), financeBudget, financeMonthlyBudget, doWarn);
        } else {
            throw new JSBadRequestException("Data not found");
        }
        return this.getFinanceDetailById(userId);
    }

    @Override
    public Finance updateFinanceCurrentBudget(Long userId, Double financeBudget) throws JSBadRequestException {
        financeRepository.updateCurrentBudget(userId, financeBudget);
        return this.getFinanceDetailById(userId);
    }

    @Override
    public void removeFinanceIncludingTransaction(Long userId) throws JSResourceNotFoundException {
        this.getFinanceDetailById(userId);
        financeRepository.removeById(userId);
    }

    @Override
    public void resetMonthlyBudget() throws JSResourceNotFoundException {
        financeRepository.resetMonthlyBudget();
    }
}
