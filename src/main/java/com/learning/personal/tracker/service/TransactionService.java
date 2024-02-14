package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransaction(Long userId);

    Transaction getTransactionById(Long userId, Long transactionId) throws JSResourceNotFoundException;

    Transaction addTransaction(Long userId, Long categoryId, Integer transactionNumeral, String transactionName, String transactionDescription, LocalDateTime transactionDate) throws JSBadRequestException;

    Transaction updateTransaction(Long userId, Long transactionId, Long categoryId, Integer transactionNumeral, String transactionName, String transactionDescription, LocalDateTime transactionDate) throws  JSBadRequestException;

    void removeTransactionDetails(Long userId, Long transactionId) throws JSResourceNotFoundException;
}
