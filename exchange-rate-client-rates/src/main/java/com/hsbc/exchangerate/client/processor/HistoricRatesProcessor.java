package com.hsbc.exchangerate.client.processor;

import com.google.common.collect.Iterables;
import com.hsbc.exchangerate.client.repository.ExchangeRatesRepository;
import com.hsbc.exchangerate.client.service.RatesApiService;
import com.hsbc.exchangerate.core.model.HistoricRatesResponse;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.persistence.entity.ExchangeRatesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class HistoricRatesProcessor {

    @Autowired
    private RatesApiService unitedRewardsService;

    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;

    public void processHistoricRates() throws RestException, ParseException {

        for (int i = 0; i < 6; i++) {
            String historicalDate = getHistoricalDate(i);
            HistoricRatesResponse historicalRates = unitedRewardsService.getHistoricalRates(historicalDate,"EUR", "GBP,HKD,USD");
            Map<String, BigDecimal> rates = historicalRates.getRates();
            for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
                ExchangeRatesEntity exchangeRatesEntity = exchangeRatesRepository.findByCurrencyCodeAndExchangeRateDate(entry.getKey(), new SimpleDateFormat("yyyy-MM-dd").parse(historicalRates.getDate()));
             //   ExchangeRatesEntity exchangeRatesEntity1 = exchangeRatesRepository.findByBaseAndCurrencyCode(historicalRates.getBase(),entry.getKey());
                if (exchangeRatesEntity != null) {
                    exchangeRatesEntity.setRate(entry.getValue());
                } else {
                    exchangeRatesEntity = ExchangeRatesEntity.builder().base(historicalRates.getBase()).exchangeRateDate(new SimpleDateFormat("yyyy-MM-dd").parse(historicalRates.getDate())).
                            currencyCode(entry.getKey()).rate(entry.getValue()).build();
                }
                exchangeRatesRepository.save(exchangeRatesEntity);
            }

        }

        Iterable<ExchangeRatesEntity> ratesEntities = exchangeRatesRepository.findAll();
        System.out.println("Total Size " + Iterables.size(ratesEntities));

    }

    private String getHistoricalDate(int month) {
        Calendar cal = Calendar.getInstance();  //Get current date/month
        cal.add(Calendar.MONTH, -month);   //Go to date, 6 months ago
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        String strDate = null;
        if (date != null) {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            strDate = ft.format(date);
        }
        return strDate;
    }
}
