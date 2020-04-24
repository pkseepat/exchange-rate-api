package com.hsbc.exchangerate.client.service;

import com.hsbc.exchangerate.client.rest.RatesApiUrlBuilder;
import com.hsbc.exchangerate.core.model.HistoricRatesResponse;
import com.hsbc.exchangerate.core.model.LatestRatesResponse;
import com.hsbc.exchangerate.core.model.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
public class RatesApiService implements RatesInterface {
    private static final String URL_LOG_MSG = "Calling URL {}";
    @Autowired
    private RatesApiUrlBuilder urlBuilder;

    @Autowired
    @Qualifier("ratesApiRestTemplate")
    private RestTemplate ratesApiRestTemplate;

    public LatestRatesResponse getLatestRates() throws RestException {
        try {
            URI url = urlBuilder.forGetLatestRates();

            log.info(URL_LOG_MSG, url);

            return ratesApiRestTemplate.getForObject(url, LatestRatesResponse.class);

        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    public HistoricRatesResponse getHistoricalRates(String historicDate,String baseCurrency,String symbols) throws RestException {
        try {
            URI url = urlBuilder.forGetHistoricalRates(historicDate, baseCurrency, symbols);

            log.info(URL_LOG_MSG, url);

            return ratesApiRestTemplate.getForObject(url, HistoricRatesResponse.class);

        } catch (Exception e) {
            throw new RestException(e);
        }
    }
}
