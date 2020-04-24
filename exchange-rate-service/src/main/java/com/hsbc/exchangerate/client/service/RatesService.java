package com.hsbc.exchangerate.client.service;

import com.hsbc.exchangerate.core.model.CurrencyRate;
import com.hsbc.exchangerate.core.model.LatestRatesResponse;
import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.Rates;
import com.hsbc.exchangerate.core.model.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatesService {

    @Autowired
    private RatesApiService unitedRewardsService;

    public Map<String, BigDecimal> getLatestRates() throws RestException {
    LatestRatesResponse latestRates = unitedRewardsService.getLatestRates();
      Map<String, BigDecimal> result = latestRates.getRates().entrySet()
                .stream()
                .filter(map -> map.getKey().equalsIgnoreCase("GBP")
                ||map.getKey().equalsIgnoreCase("USD")|| map.getKey().equalsIgnoreCase("HKD"))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        return result;
    }

    public List<CurrencyRate> getHistoricalRates() throws RestException {
        List<CurrencyRate> currencyRateList = new ArrayList<CurrencyRate>();

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCurrencyCode("GBP");

        List<Rate> rates = new ArrayList<Rate>();
        Rate rate1 = new Rate();
        rate1.setCurrencyCode("GBP");
        rate1.setMonth(1);
        rate1.setRate(new BigDecimal(1.0));
        rates.add(rate1);
        Rate rate2 = new Rate();
        rate2.setCurrencyCode("GBP");
        rate2.setMonth(1);
        rate2.setRate(new BigDecimal(1.0));
        rates.add(rate2);
        Rate rate3 = new Rate();
        rate3.setCurrencyCode("GBP");
        rate3.setMonth(1);
        rate3.setRate(new BigDecimal(1.0));
        rates.add(rate3);

        currencyRate.setRates(rates);

        currencyRateList.add(currencyRate);

        CurrencyRate currencyRate1 = new CurrencyRate();
        currencyRate1.setCurrencyCode("USA");

        List<Rate> rates1 = new ArrayList<Rate>();
        Rate rate5 = new Rate();
        rate5.setCurrencyCode("USA");
        rate5.setMonth(1);
        rate5.setRate(new BigDecimal(2.0));
        rates1.add(rate5);
        Rate rate6 = new Rate();
        rate6.setCurrencyCode("USA");
        rate6.setMonth(1);
        rate6.setRate(new BigDecimal(1.0));
        rates1.add(rate6);
        Rate rate7 = new Rate();
        rate7.setCurrencyCode("USA");
        rate7.setMonth(1);
        rate7.setRate(new BigDecimal(1.0));
        rates1.add(rate7);
        currencyRate1.setRates(rates1);

        currencyRateList.add(currencyRate1);

        return currencyRateList;
    }
}
