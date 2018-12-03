package com.example.OperationDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "operations")
@Getter
@Setter
@ToString
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate execDate;

    private LocalDate orderDate;

    private String type;

    @Column(columnDefinition = "text")
    private String description;

    private BigDecimal amount;

    @Column(length = 3)
    private String amountCurr;

    private BigDecimal endingBalance;

    @Column(length = 3)
    private String endingBalanceCurr;
}
