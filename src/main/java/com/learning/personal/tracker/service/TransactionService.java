package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransaction(Long userId);

    Transaction getTransactionById(Long userId, Long transactionId) throws JSResourceNotFoundException;

    Transaction addTransaction(Long userId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException;

    void updateTransaction(Long userId, Long transactionId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws  JSBadRequestException;

    void removeTransactionDetails(Long userId, Long transactionId) throws JSResourceNotFoundException;
}
