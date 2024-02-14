package com.learning.personal.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction_list")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", columnDefinition = "serial")
    private Long transactionId;
    @Column(name = "transaction_numeral", nullable = false)
    private Integer transactionNumeral;
    @Column(name = "transaction_name")
    private String transactionName;
    @Column(name = "transaction_description")
    private String transactionDescription;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "finance_id")
    @JsonIgnore
    private Finance finance;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TransactionCategory category;

    @Transient
    private Long financeId;

    public Transaction(Long transactionId, Long financeId, Integer transactionNumeral, String transactionName, String transactionDescription, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.financeId = financeId;
        this.transactionNumeral = transactionNumeral;
        this.transactionName = transactionName;
        this.transactionDescription = transactionDescription;
        this.transactionDate = transactionDate;
    }
}
