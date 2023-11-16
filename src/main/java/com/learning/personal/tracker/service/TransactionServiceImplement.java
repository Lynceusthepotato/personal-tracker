package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;
import com.learning.personal.tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImplement implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransaction(Long userId) {
        return transactionRepository.findAll(userId);
    }

    @Override
    public Transaction getTransactionById(Long userId, Long transactionId) throws JSResourceNotFoundException {
        return transactionRepository.findById(userId, transactionId);
    }

    @Override
    public Transaction addTransaction(Long userId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException {
        Long transactionId = transactionRepository.create(userId, transactionNumeral, transactionDescription, transactionDate);
        return transactionRepository.findById(userId, transactionId);
    }

    @Override
    public void updateTransaction(Long userId, Long transactionId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) throws JSBadRequestException {
        transactionRepository.update(userId, transactionId, transactionNumeral, transactionDescription, transactionDate);
    }

    @Override
    public void removeTransactionDetails(Long userId, Long transactionId) throws JSResourceNotFoundException {
        transactionRepository.removeById(userId, transactionId);
    }
}
