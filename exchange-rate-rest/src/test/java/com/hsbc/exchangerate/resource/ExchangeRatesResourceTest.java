package com.hsbc.exchangerate.resource;

import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.RatesApiResponse;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.resource.data.DummyExchangeRatesBuilder;
import com.hsbc.exchangerate.service.RatesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRatesResourceTest {

    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @InjectMocks
    private ExchangeRatesResource exchangeRatesResource;

    @Mock
    private RatesService ratesService;

    @Test
    public void getLatestRates_should_callServiceAndReturnResult() throws Exception {

        // GIVEN
        List<Rate> expected = DummyExchangeRatesBuilder.createDummyExchangeRatesList();

        given(ratesService.getLatestRates()).willReturn(expected);
        // WHEN
        RatesApiResponse<List<Rate>> latestRates = exchangeRatesResource.getLatestRates();

        // THEN
        assertNull(latestRates.getStatus().getErrorCode());
        assertNull(latestRates.getStatus().getErrorMessage());
        assertEquals(expected, latestRates.getData());
    }

    @Test
    public void getLatestRates_should_returnErrorMessage_when_RatesApiReturnsException() throws Exception {
        // GIVEN
        RestException restException = new RestException(ERROR_CODE, ERROR_MESSAGE);
        given(ratesService.getLatestRates()).willThrow(restException);

        // WHEN
        RatesApiResponse<List<Rate>> latestRates = exchangeRatesResource.getLatestRates();

        // THEN
        assertFalse(latestRates.getStatus().isSuccess());
        assertEquals(ERROR_CODE, latestRates.getStatus().getErrorCode());
        assertEquals(ERROR_MESSAGE, latestRates.getStatus().getErrorMessage());
    }
}