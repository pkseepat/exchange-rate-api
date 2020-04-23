package com.hsbc.exchangerate.resource;

import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.RatesApiResponse;
import com.hsbc.exchangerate.core.model.Status;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.service.RatesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("Exchange Rate Service")
@RestController
@RequestMapping("/api/rates")
@Slf4j
public class ExchangeRatesResource {

    @Autowired
    private RatesService ratesService;

    @GetMapping("/latest")
    public RatesApiResponse<List<Rate>> getLatestRates() {
        log.info("GET request for latest rates {}");
        RatesApiResponse<List<Rate>> ratesApiResponse = new RatesApiResponse();
        try {
            List<Rate> latestRates = ratesService.getLatestRates();
            ratesApiResponse.setData(latestRates);
            ratesApiResponse.setStatus(new Status(true, null, null));
        } catch (RestException e) {
            ratesApiResponse.setData(null);
            ratesApiResponse.setStatus(e.getStatus());
        }
        return ratesApiResponse;
    }
}
