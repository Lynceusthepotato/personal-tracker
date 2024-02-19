package com.learning.personal.tracker.controller;

import com.learning.personal.tracker.model.TransactionCategory;
import com.learning.personal.tracker.service.TransactionCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction_category")
public class TransactionCategoryController {

    @Autowired
    TransactionCategoryService transactionCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<TransactionCategory>> getAllTransactionCategory() {
        List<TransactionCategory> categoryList = transactionCategoryService.getAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

}
