package com.hsbc.exchangerate.service;

import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.exception.RestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class RatesService {

    public List<Rate> getLatestRates() throws RestException {
        List<Rate> rates = new ArrayList<Rate>();
        Rate rate1 = new Rate();
        rate1.setCurrencyCode("GBP");
        rate1.setRate(new BigDecimal(1.0));
        Rate rate2 = new Rate();
        rate2.setCurrencyCode("USA");
        rate2.setRate(new BigDecimal(2.0));
        Rate rate3 = new Rate();
        rate3.setCurrencyCode("UK");
        rate3.setRate(new BigDecimal(3.0));

        rates.add(rate1);
        rates.add(rate2);
        rates.add(rate3);

        return rates;
    }
}
