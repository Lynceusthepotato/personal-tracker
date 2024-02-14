package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.TransactionCategory;
import com.learning.personal.tracker.repository.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionCategoryServiceImplement implements TransactionCategoryService{

    @Autowired
    TransactionCategoryRepository transactionCategoryRepository;

    @Override
    public List<TransactionCategory> getAllCategory() {
        return transactionCategoryRepository.findAll();
    }

    @Override
    public TransactionCategory getTransCategoryById(Long categoryId) throws JSResourceNotFoundException {
        return transactionCategoryRepository.findById(categoryId);
    }

    @Override
    public TransactionCategory getTransCategoryByTransactionId(Long transactionId) throws JSResourceNotFoundException {
        return transactionCategoryRepository.findByTransactionId(transactionId);
    }

    @Override
    public TransactionCategory addCategory(String categoryName) throws JSBadRequestException {
        Long categoryId = transactionCategoryRepository.create(categoryName);
        return transactionCategoryRepository.findById(categoryId);
    }
}
