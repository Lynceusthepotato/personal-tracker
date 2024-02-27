package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Finance;

public interface FinanceRepository {

//    List<Finance> findAll(Long userId) throws JSResourceNotFoundException; don`t need this cause finance is one to one

    Finance findById(Long userId) throws JSResourceNotFoundException;

    Long create(Long userId, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException;

    void update(Long userId, Long finance_id, Double financeBudget, Double financeMonthlyBudget, boolean doWarn) throws JSBadRequestException;

    void removeById(Long userId) throws JSResourceNotFoundException;

    void updateCurrentBudget(Long userId, Double financeBudget) throws JSBadRequestException;
}
