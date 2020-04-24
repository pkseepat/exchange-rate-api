package com.hsbc.exchangerate.resource;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hsbc.exchangerate.client.service.RatesService;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.resource.data.DummyExchangeRatesBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(exchangeRatesResource).build();
    }


    @Test
    public void getLatestRates_should_callServiceAndReturnResult() throws Exception {

        // GIVEN
        Map<String, BigDecimal> expected = DummyExchangeRatesBuilder.createDummyExchangeRatesList();

        given(ratesService.getLatestRates()).willReturn(expected);

        // THEN
        this.mockMvc.perform(get(API_PROMOTIONS_BATCH + "/latest")
        ).andExpect(status().isOk())
                .andExpect(model().attributeExists("latestRates"));
    }

    @Test
    public void getLatestRates_should_returnErrorMessage_when_RatesApiReturnsException() throws Exception {
        // GIVEN
        RestException restException = new RestException(ERROR_CODE, ERROR_MESSAGE);
        given(ratesService.getLatestRates()).willThrow(restException);

        // THEN
        this.mockMvc.perform(get(API_PROMOTIONS_BATCH + "/latest")
        ).andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("latestRates"))
                        .andExpect(view().name("showLatestRates"));
    }
}