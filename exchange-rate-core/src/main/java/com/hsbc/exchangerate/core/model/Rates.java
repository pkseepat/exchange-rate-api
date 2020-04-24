package com.hsbc.exchangerate.core.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rates {
    private double  GBP;
    private double  HKD;
    private double USD;
}
