package com.learning.personal.tracker.repository;

import com.learning.personal.tracker.exceptions.JSBadRequestException;
import com.learning.personal.tracker.exceptions.JSResourceNotFoundException;
import com.learning.personal.tracker.model.TransactionCategory;
import com.learning.personal.tracker.service.TransactionCategoryService;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TransactionCategoryRepository {

    List<TransactionCategory> findAll();

    TransactionCategory findById(Long categoryId) throws JSResourceNotFoundException;

    TransactionCategory findByTransactionId(Long transactionId) throws  JSResourceNotFoundException;

    Long create(String categoryName) throws JSBadRequestException;
}
