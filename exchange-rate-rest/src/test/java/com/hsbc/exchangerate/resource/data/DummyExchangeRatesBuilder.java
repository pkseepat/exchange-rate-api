package com.hsbc.exchangerate.resource.data;

import com.hsbc.exchangerate.core.model.Rate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DummyExchangeRatesBuilder {
    public static List<Rate> createDummyExchangeRatesList() {
        List<Rate> rates = new ArrayList<Rate>();
        Rate rate1 = new Rate();
        rate1.setCurrencyCode("GBP");
        rate1.setRate(new BigDecimal(1.0));
        Rate rate2 = new Rate();
        rate1.setCurrencyCode("USA");
        rate1.setRate(new BigDecimal(2.0));
        Rate rate3 = new Rate();
        rate1.setCurrencyCode("UK");
        rate1.setRate(new BigDecimal(3.0));

        rates.add(rate1);
        rates.add(rate2);
        rates.add(rate3);

        return rates;
    }
}
