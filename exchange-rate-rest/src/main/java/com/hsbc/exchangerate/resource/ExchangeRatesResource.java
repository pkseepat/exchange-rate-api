package com.hsbc.exchangerate.resource;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Exchange Rate Service")
@RestController
@RequestMapping("/api/rates")
@Slf4j
public class ExchangeRatesResource {

    @GetMapping("/latest")
    public ResponseEntity getLatestRates() {
        log.info("GET request for latest rates {}");
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
