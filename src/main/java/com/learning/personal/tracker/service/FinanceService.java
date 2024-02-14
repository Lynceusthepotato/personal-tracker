package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Finance;

public interface FinanceService {

    Finance getFinanceDetailById(Long userId) throws JSResourceNotFoundException;

    Finance addFinance(Long userId, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException;

    Finance updateFinance(Long userId, Double financeBudget, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException;
    void removeFinanceIncludingTransaction(Long userId) throws JSResourceNotFoundException;
}
