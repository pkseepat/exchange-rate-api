package com.hsbc.exchangerate.client.service;

import com.hsbc.exchangerate.core.model.LatestRatesResponse;
import com.hsbc.exchangerate.core.model.exception.RestException;

public interface RatesInterface {

    LatestRatesResponse getLatestRates() throws RestException;
}
