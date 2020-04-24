package com.hsbc.exchangerate.scheduler;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsbc.exchangerate.client.processor.HistoricRatesProcessor;
import com.hsbc.exchangerate.core.model.exception.RestException;

import lombok.extern.slf4j.Slf4j;

@Async
@Component
@Slf4j
public class HistoricRatesScheduler {

    @Autowired
    private HistoricRatesProcessor historicRatesProcessor;

    /**
     * Method to run a schduled job daily to updates rates in database
     */
    @Scheduled(fixedDelayString  = "86400000")
    @Transactional
    public void runScheduledHistoricRates() {
        try {
            historicRatesProcessor.processHistoricRates();
        } catch (RestException | ParseException e) {
            log.error("Error running scheduled historic rates processor");
        }
    }
}
