package com.hsbc.exchangerate.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRatesResourceTest {

    @InjectMocks
    private ExchangeRatesResource exchangeRatesResource;

    @Test
    public void getLatestRrates_should_callServiceAndReturnResult() throws Exception {
        // WHEN
        ResponseEntity latestRates = exchangeRatesResource.getLatestRates();

        // THEN
        assertFalse(latestRates.getStatusCode().isError());
        assertTrue(latestRates.getStatusCode().is2xxSuccessful());
      //  assertEquals(expected, result.getData());
    }
}