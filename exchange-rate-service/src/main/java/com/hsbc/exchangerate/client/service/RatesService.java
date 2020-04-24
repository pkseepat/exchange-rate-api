package com.hsbc.exchangerate.client.service;

import com.hsbc.exchangerate.client.repository.ExchangeRatesRepository;
import com.hsbc.exchangerate.core.model.*;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.persistence.entity.ExchangeRatesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatesService {

    @Autowired
    private RatesApiService unitedRewardsService;

    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;

    public Map<String, BigDecimal> getLatestRates() throws RestException {
    LatestRatesResponse latestRates = unitedRewardsService.getLatestRates();
      Map<String, BigDecimal> result = latestRates.getRates().entrySet()
                .stream()
                .filter(map -> map.getKey().equalsIgnoreCase("GBP")
                ||map.getKey().equalsIgnoreCase("USD")|| map.getKey().equalsIgnoreCase("HKD"))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        return result;
    }

    public Map<String, List<BigDecimal>> getHistoricalRates() throws RestException, ParseException {
        List<BigDecimal> gbpRateList = mapHistoricalData("GBP");
        List<BigDecimal> usdRateList = mapHistoricalData("USD");
        List<BigDecimal> hkdRateList = mapHistoricalData("HKD");
        Map<String, List<BigDecimal>> historicalList = new HashMap<>();
        historicalList.put("GBP", gbpRateList);
        historicalList.put("USD", usdRateList);
        historicalList.put("HKD", hkdRateList);
        return historicalList;

    }

    private List<BigDecimal> mapHistoricalData(String currencyCode) throws ParseException {
        Date historicalFromDate = getHistoricalDate(0);
        Date historicalToDate = getHistoricalDate(6);
        List<ExchangeRatesEntity> gbpHistoricalData1 = exchangeRatesRepository.findALLByExchangeRateDateBetweenAndCurrencyCode(new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-01"), new SimpleDateFormat("yyyy-MM-dd").parse("2019-10-01"), currencyCode);
        List<ExchangeRatesEntity> gbpHistoricalData = exchangeRatesRepository.findALLByCurrencyCode(currencyCode);
        CurrencyRate currencyRate10 = new CurrencyRate();
        currencyRate10.setCurrencyCode(currencyCode);
        List<Rate> rates10 = new ArrayList<Rate>();

        List<BigDecimal> gbpRateList = new ArrayList<>();
        gbpHistoricalData.forEach(item -> {
            gbpRateList.add(item.getRate());
        });
        return gbpRateList;
    }

    private Date getHistoricalDate(int month) {
        Calendar cal = Calendar.getInstance();  //Get current date/month
        cal.add(Calendar.MONTH, -month);   //Go to date, 6 months ago
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
      //  String strDate = null;
        /*if (date != null) {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            strDate = ft.format(date);
        }*/
        return cal.getTime();
    }
}
