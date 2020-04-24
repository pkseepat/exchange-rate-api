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


    @Value(value = "${rates.api.baseurl:https://api.ratesapi.io/api/}")
    private String serviceUrl;

    public URI forGetLatestRates() throws URISyntaxException {
        URIBuilder builder = initializeBuilder(GET_LATEST_URL);
        return builder.build();
    }

    private URIBuilder initializeBuilder(String url) throws URISyntaxException {
        String baseUrl = String.format(serviceUrl);
        URIBuilder builder = new URIBuilder(baseUrl + url);
        builder.addParameter("JSO-N_CT", "application/JSON");
        return builder;
    }
}
