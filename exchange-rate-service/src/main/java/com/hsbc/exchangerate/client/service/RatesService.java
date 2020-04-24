package com.hsbc.exchangerate.client.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsbc.exchangerate.client.repository.ExchangeRatesRepository;
import com.hsbc.exchangerate.core.model.CurrencyRate;
import com.hsbc.exchangerate.core.model.LatestRatesResponse;
import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.persistence.entity.ExchangeRatesEntity;

@Service
public class RatesService {

    @Autowired
    private RatesApiService unitedRewardsService;

    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;

    /**
     * Method to get latest rates from rates API
     * @return
     * @throws RestException
     */
    public Map<String, BigDecimal> getLatestRates() throws RestException {
    LatestRatesResponse latestRates = unitedRewardsService.getLatestRates();
      Map<String, BigDecimal> result = latestRates.getRates().entrySet()
                .stream()
                .filter(map -> map.getKey().equalsIgnoreCase("GBP")
                ||map.getKey().equalsIgnoreCase("USD")|| map.getKey().equalsIgnoreCase("HKD"))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        return result;
    }

    /**
     * Method to get past 6 days historical rates
     * @return
     * @throws RestException
     * @throws ParseException
     */
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
    /*Method  to get historic data for three curriencies*/
    private List<BigDecimal> mapHistoricalData(String currencyCode) throws ParseException {
        Date historicalFromDate = getHistoricalDate(0);
        Date historicalToDate = getHistoricalDate(6);
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
     /*Get Date for a given month*/
    private Date getHistoricalDate(int month) {
        Calendar cal = Calendar.getInstance();  //Get current date/month
        cal.add(Calendar.MONTH, -month);   //Go to date, 6 months ago
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
}
