package com.hsbc.exchangerate.core.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class Rate {
    private String currencyCode;
    private BigDecimal rate;
}
