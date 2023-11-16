package com.learning.personal.tracker.controller;

import com.learning.personal.tracker.model.Finance;
import com.learning.personal.tracker.service.FinanceService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    FinanceService financeService;

    @PostMapping("/create")
    public ResponseEntity<Finance> addFinance(HttpServletRequest request, @RequestParam Map<String, Object> financeMap) {
        Long userId = (Long) request.getAttribute("user_id");
        Integer financeBudget = Integer.parseInt ((String) financeMap.get("finance_budget"));
        boolean doWarn = Boolean.parseBoolean((String) financeMap.get("do_warn"));
        Finance finance = financeService.addFinance(userId, financeBudget, doWarn);
        return new ResponseEntity<>(finance, HttpStatus.CREATED);
    }

    @PostMapping("/find")
    public ResponseEntity<Finance> findFinance(HttpServletRequest request) { // could add path variable for finance_id but since each user is finance detail unique then it not need to
        Long userId = (Long) request.getAttribute("user_id");
        Finance finance = financeService.getFinanceDetailById(userId);
        return new ResponseEntity<>(finance, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Boolean>> updateFinance(HttpServletRequest request, @RequestParam Map<String, Object> financeMap) {
        Long userId = (Long) request.getAttribute("user_id");
        Integer financeBudget = Integer.parseInt ((String) financeMap.get("finance_budget"));
        boolean doWarn = Boolean.parseBoolean((String) financeMap.get("do_warn"));
        financeService.updateFinance(userId, financeBudget, doWarn);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Boolean>> deleteFinance(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user_id");
        financeService.removeFinanceIncludingTransaction(userId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
