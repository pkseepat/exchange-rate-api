package com.hsbc.exchangerate.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CurrencyRate {
    private Integer month;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date historicalDate;
    private List<Rate> rates;
    private String currencyCode;
}
