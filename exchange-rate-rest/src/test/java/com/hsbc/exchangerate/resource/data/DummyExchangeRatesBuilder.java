package com.hsbc.exchangerate.resource.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DummyExchangeRatesBuilder {
    public static Map<String, BigDecimal> createDummyExchangeRatesList() {
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        rates.put("GBP",new BigDecimal(1.0));
        rates.put("USA",new BigDecimal(2.0));
        rates.put("UK",new BigDecimal(3.0));
        return rates;
    }
}
