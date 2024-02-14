package com.learning.personal.tracker.service;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.TransactionCategory;

import java.util.List;

public interface TransactionCategoryService {

    List<TransactionCategory> getAllCategory();

    TransactionCategory getTransCategoryById(Long categoryId) throws JSResourceNotFoundException;

    TransactionCategory getTransCategoryByTransactionId(Long transaction_id) throws JSResourceNotFoundException;

    TransactionCategory addCategory(String categoryName) throws JSBadRequestException;
}
