package com.hsbc.exchangerate.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricRatesResponse {
    private String base;
    private String date;
    //private Rates rates;
    private Map<String, BigDecimal> rates;
}
