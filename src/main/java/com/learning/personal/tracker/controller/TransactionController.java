package com.learning.personal.tracker.controller;

import com.learning.personal.tracker.model.Finance;
import com.learning.personal.tracker.model.Transaction;
import com.learning.personal.tracker.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransaction (HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user_id");
        List<Transaction> transaction = transactionService.getAllTransaction(userId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/find/{transaction_id}")
    public ResponseEntity<Transaction> findTransaction(HttpServletRequest request, @PathVariable("transaction_id") Long transactionId) {
        Long userId = (Long) request.getAttribute("user_id");
        Transaction transaction = transactionService.getTransactionById(userId, transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request, @RequestParam Map<String, Object> transactionMap) {
        Long userId = (Long) request.getAttribute("user_id");
        Long categoryId = Long.parseLong((String) transactionMap.get("category_id"));
        Integer transactionNumeral = Integer.parseInt((String) transactionMap.get("transaction_numeral"));
        String transactionName = (String) transactionMap.get("transaction_name");
        String transactionDescription = (String) transactionMap.get("transaction_description");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse((String) transactionMap.get("transaction_date"), formatter);
        Transaction transaction = transactionService.addTransaction(userId, categoryId, transactionNumeral, transactionName, transactionDescription, date);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(HttpServletRequest request, @RequestParam Map<String, Object> transactionMap) {
        Long userId = (Long) request.getAttribute("user_id");
        Long transactionId = Long.parseLong((String) transactionMap.get("transaction_id"));
        Long categoryId = Long.parseLong((String) transactionMap.get("category_id"));
        Integer transactionNumeral = Integer.parseInt((String) transactionMap.get("transaction_numeral"));
        String transactionName = (String) transactionMap.get("transaction_name");
        String transactionDescription = (String) transactionMap.get("transaction_description");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse((String) transactionMap.get("transaction_date"), formatter);
        Transaction transaction = transactionService.updateTransaction(userId, transactionId, categoryId, transactionNumeral, transactionName, transactionDescription, date);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{transaction_id}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request, @PathVariable("transaction_id") Long transactionId) {
        Long userId = (Long) request.getAttribute("user_id");
        transactionService.removeTransactionDetails(userId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
