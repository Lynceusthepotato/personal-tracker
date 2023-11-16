package com.learning.personal.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "finance_list")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finance_id", columnDefinition = "serial")
    private Long financeId;
    @Column(name = "finance_budget")
    private Integer financeBudget;
    @Column(name = "do_warn")
    private boolean doWarn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user; // Don`t want this on the return only to initialize the DB
    @Transient
    private Long userId; // Don`t want this to get initialized on DB cause already have the join column
    @OneToMany(mappedBy = "finance")
    private List<Transaction> transaction;

    public Finance(long financeId, Long userId, Integer financeBudget, boolean doWarn) {
        this.financeId = financeId;
        this.userId = userId;
        this.financeBudget = financeBudget;
        this.doWarn = doWarn;
    }

}
