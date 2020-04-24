package com.hsbc.exchangerate.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private String currencyCode;
    private BigDecimal rate;
    private Integer days;
    private Integer month;
}
