package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.Transaction;
import com.learning.personal.tracker.repository.FinanceRepository;
import com.learning.personal.tracker.repository.TransactionCategoryRepository;
import com.learning.personal.tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImplement implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionCategoryRepository transactionCategoryRepository;
    @Autowired
    FinanceRepository financeRepository;

    @Override
    public List<Transaction> getAllTransaction(Long userId) {
        List<Transaction> transactionList = transactionRepository.findAll(userId);
        for (Transaction transaction : transactionList) {
            transaction.setCategory(transactionCategoryRepository.findByTransactionId(transaction.getTransactionId()));
        }
        return transactionList;
    }

    @Override
    public Transaction getTransactionById(Long userId, Long transactionId) throws JSResourceNotFoundException {
        Transaction tempTransaction = transactionRepository.findById(userId, transactionId);
        tempTransaction.setCategory(transactionCategoryRepository.findByTransactionId(transactionId));
        return tempTransaction;
    }

    @Override
    public Transaction addTransaction(Long userId, Long categoryId, Integer transactionNumeral, String transactionName, String transactionDescription, LocalDateTime transactionDate) throws JSBadRequestException {
        Long transactionId = transactionRepository.create(userId, categoryId, transactionNumeral, transactionName, transactionDescription, transactionDate);
        if (transactionId != null ) {
            financeRepository.updateCurrentBudget(userId, (double) transactionNumeral);
        }
        return getTransactionById(userId, transactionId);
    }

    @Override
    public Transaction updateTransaction(Long userId, Long transactionId, Long categoryId, Integer transactionNumeral, String transactionName, String transactionDescription, LocalDateTime transactionDate) throws JSBadRequestException {
        Transaction tempTransaction = this.getTransactionById(userId, transactionId);
        transactionRepository.update(userId, transactionId, categoryId, transactionNumeral, transactionName, transactionDescription, transactionDate);
        Double calcCurrency;
        if (tempTransaction.getTransactionNumeral() > transactionNumeral) {
            calcCurrency = (double) ((tempTransaction.getTransactionNumeral() - transactionNumeral) * -1); // For my self in the future... why make it negative, it is because Update current currency sql is current - x
        } else {
            calcCurrency = (double) (tempTransaction.getTransactionNumeral() - transactionNumeral);
        }
        financeRepository.updateCurrentBudget(userId, calcCurrency);
        return getTransactionById(userId, transactionId);
    }

    @Override
    public void removeTransactionDetails(Long userId, Long transactionId) throws JSResourceNotFoundException {
        Transaction tempTransaction = this.getTransactionById(userId, transactionId);
        if (tempTransaction != null ) {
            financeRepository.updateCurrentBudget(userId, (double) (tempTransaction.getTransactionNumeral() * -1));
        }
        transactionRepository.removeById(userId, transactionId);

    }
}
