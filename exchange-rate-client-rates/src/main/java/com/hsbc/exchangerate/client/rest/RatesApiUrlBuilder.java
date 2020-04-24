package com.hsbc.exchangerate.client.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class RatesApiUrlBuilder {
    public static final String GET_LATEST_URL = "/latest";
    public static final String GET_HISTORICAL_URL = "/*";


    @Value(value = "${rates.api.baseurl:https://api.ratesapi.io/api/}")
    private String serviceUrl;

    /**
     * Methodto get Latest rates API URI
     * @return
     * @throws URISyntaxException
     */
    public URI forGetLatestRates() throws URISyntaxException {
        URIBuilder builder = initializeBuilder(GET_LATEST_URL);
        return builder.build();
    }

    /**
     * Method to get historic rates URI
     * @param historicDate
     * @param baseCurrency
     * @param symbols
     * @return
     * @throws URISyntaxException
     */
    public URI forGetHistoricalRates(String historicDate,String baseCurrency,String symbols) throws URISyntaxException {
        URIBuilder builder = initializeBuilder(historicDate, baseCurrency, symbols);
        return builder.build();
    }


    private URIBuilder initializeBuilder(String url) throws URISyntaxException {
        String baseUrl = String.format(serviceUrl);
        URIBuilder builder = new URIBuilder(baseUrl + url);
        return builder;
    }

    private URIBuilder initializeBuilder(String historicDate,String baseCurrency,String symbols) throws URISyntaxException {
        String baseUrl = String.format(serviceUrl);
        URIBuilder builder = new URIBuilder(baseUrl + "/" + historicDate);
        builder.addParameter("base", baseCurrency);
        builder.addParameter("symbols", symbols);
        return builder;
    }
}
