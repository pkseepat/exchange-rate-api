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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRatesResourceTest {
    public static final String API_PROMOTIONS_BATCH = "/api/rates";
    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @Autowired
    private MockMvc mockMvc;

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
      //  String latestRates = exchangeRatesResource.getLatestRates();

        // THEN
        this.mockMvc.perform(get(API_PROMOTIONS_BATCH + "/latest")
        ).andExpect(status().isOk())
                .andExpect(model().attributeExists("latestRates"));
        /*assertNull(latestRates.getStatus().getErrorCode());
        assertNull(latestRates.getStatus().getErrorMessage());
        assertEquals(expected, latestRates.getData());*/
    }

    @Test
    public void getLatestRates_should_returnErrorMessage_when_RatesApiReturnsException() throws Exception {
        // GIVEN
        RestException restException = new RestException(ERROR_CODE, ERROR_MESSAGE);
        given(ratesService.getLatestRates()).willThrow(restException);

        // WHEN
       // RatesApiResponse<List<Rate>> latestRates = exchangeRatesResource.getLatestRates();

        // THEN
      /*  assertFalse(latestRates.getStatus().isSuccess());
        assertEquals(ERROR_CODE, latestRates.getStatus().getErrorCode());
        assertEquals(ERROR_MESSAGE, latestRates.getStatus().getErrorMessage());*/
    }
}