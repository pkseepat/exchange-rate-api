package com.hsbc.exchangerate.resource;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsbc.exchangerate.client.service.RatesService;
import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.exception.RestException;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api("Exchange Rate Service")
@Controller
@RequestMapping("/api/rates")
@Slf4j
public class ExchangeRatesResource {

    public static final String SHOW_RATES = "showLatestRates";
    public static final String HISTORICAL_RATES = "showHistoricalRates";
    public static final String ERROR_MESSAGE = "message";
    public static final String REDIRECT_PROMOTIONS = "redirect:/api/promotions";

    @Autowired
    private RatesService ratesService;


    @GetMapping("/latest")
    public String getLatestRates(Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        log.info("GET request for latest rates {}");
        try {
            Map<String, BigDecimal> latestRates = ratesService.getLatestRates();
            model.addAttribute("latestRates", latestRates);
            model.addAttribute("days", "");
            model.addAttribute("rate", new Rate());
        } catch (RestException e) {
            model.addAttribute(ERROR_MESSAGE,
                    "Error getting latest exchange rates, please try again");
        }
        return SHOW_RATES;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, params = {"days"})
    public String getHistoricalRates(Model model, @RequestParam String days, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        log.info("POST request for historical rates {}");
        try {
            Map<String, List<BigDecimal>> historicalRates = ratesService.getHistoricalRates();
            model.addAttribute("historicalRates", historicalRates);
        } catch (RestException | ParseException e) {
            model.addAttribute(ERROR_MESSAGE,
                    "Error getting historic exchange rates, please try again");
        }
        return HISTORICAL_RATES;
    }
}
