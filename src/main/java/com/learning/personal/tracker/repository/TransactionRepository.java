package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll(Long userId);

    Transaction findById(Long userId, Long transactionId) throws JSResourceNotFoundException;

    Long create(Long userId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException;

    void update(Long userId, Long transactionId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws  JSBadRequestException;

    void removeById(Long userId, Long transactionId) throws JSResourceNotFoundException;
}
