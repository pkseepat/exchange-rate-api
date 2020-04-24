package com.hsbc.exchangerate.persistence.entity;

import lombok.*;

import javax.persistence.*;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exchange_rates")
public class ExchangeRatesEntity {
    @Id
    @GeneratedValue
    private long id;
    private String base;
    private String currencyCode;
    private BigDecimal rate;
    @Temporal(TemporalType.DATE)
    private Date exchangeRateDate;
}
