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
    @Column(name = "transaction_numeral")
    private Integer transactionNumeral;
    @Column(name = "transaction_description")
    private String transactionDescription;
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "finance_id")
    @JsonIgnore
    private Finance finance;

    @Transient
    private Long financeId;

    public Transaction(Long transactionId, Long financeId, Integer transactionNumeral, String transactionDescription, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.financeId = financeId;
        this.transactionNumeral = transactionNumeral;
        this.transactionDescription = transactionDescription;
        this.transactionDate = transactionDate;
    }
}
