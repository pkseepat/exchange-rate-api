package com.hsbc.exchangerate.client.service;

import com.hsbc.exchangerate.client.rest.RatesApiUrlBuilder;
import com.hsbc.exchangerate.core.model.LatestRatesResponse;
import com.hsbc.exchangerate.core.model.Rates;
import com.hsbc.exchangerate.core.model.exception.RestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatesApiServiceTest {

    private static final URI DUMMY_URL =  URI.create("dummyUrl");
    public static final String EXPECTED_BASE = "base";

    @InjectMocks
    private RatesApiService ratesApiServiceService;

    @Mock
    private RatesApiUrlBuilder urlBuilder;

    @Mock
    private RestTemplate restTemplate;

    private LatestRatesResponse latestRatesResponse;

    @Test(expected = RestException.class)
    public void getLatestRates_should_throwRestException_when_serviceThrowsException() throws Exception {
        // GIVEN
        when(urlBuilder.forGetLatestRates()).thenReturn(DUMMY_URL);
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "Expected exception");
        when(restTemplate.getForObject(DUMMY_URL, LatestRatesResponse.class)).thenThrow(ex);

        // WHEN
        LatestRatesResponse response = ratesApiServiceService.getLatestRates();

        // THEN exception thrown
    }

    @Test
    public void getSessionId_should_returnValidResponse_when_validResponseReceived() throws Exception {
        givenIHaveAValidGetSessionId();
        thenIExpectBaseToBeBuiltFromRatesApiBase(EXPECTED_BASE);
    }

    private void givenIHaveAValidGetSessionId() throws URISyntaxException {
      //  latestRatesResponse = LatestRatesResponse.builder().base(EXPECTED_BASE).date(new Date()).rates(Rates.builder().GBP(new BigDecimal(0.8972)).build()).build();
        when(urlBuilder.forGetLatestRates()).thenReturn(DUMMY_URL);
        when(restTemplate.getForObject(DUMMY_URL, LatestRatesResponse.class)).thenReturn(latestRatesResponse);
    }

    private void thenIExpectBaseToBeBuiltFromRatesApiBase(String expectedBase) {
        Assert.assertEquals(expectedBase, latestRatesResponse.getBase());
    }
}