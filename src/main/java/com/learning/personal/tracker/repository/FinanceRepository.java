package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Finance;

public interface FinanceRepository {

//    List<Finance> findAll(Long userId) throws JSResourceNotFoundException; don`t need this cause finance is one to one

    Finance findById(Long userId) throws JSResourceNotFoundException;

    Long create(Long userId, Integer financeBudget, boolean doWarn) throws JSBadRequestException;

    void update(Long userId, Long finance_id, Integer financeBudget, boolean doWarn) throws JSBadRequestException;

    void removeById(Long userId) throws JSResourceNotFoundException;
}