package com.learning.personal.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableScheduling
public class MonthlyReset {

    @Autowired
    FinanceService financeService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateMonthlyBudget() {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.getDayOfMonth() == 1) {
            financeService.resetMonthlyBudget();
        }
    }
}
