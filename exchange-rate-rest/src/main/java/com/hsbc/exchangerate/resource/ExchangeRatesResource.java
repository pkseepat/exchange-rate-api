package com.hsbc.exchangerate.resource;

import com.hsbc.exchangerate.core.model.Rate;
import com.hsbc.exchangerate.core.model.RatesApiResponse;
import com.hsbc.exchangerate.core.model.Status;
import com.hsbc.exchangerate.core.model.exception.RestException;
import com.hsbc.exchangerate.service.RatesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("Exchange Rate Service")
@Controller
@RequestMapping("/api/rates")
@Slf4j
public class ExchangeRatesResource {

    public static final String SHOW_RATES = "showLatestRates";

    @Autowired
    private RatesService ratesService;


    @GetMapping("/latest")
    public String getLatestRates(Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
       // log.info("GET request for latest rates {}");
        RatesApiResponse<List<Rate>> ratesApiResponse = new RatesApiResponse();
        try {
            List<Rate> latestRates = ratesService.getLatestRates();
           // ratesApiResponse.setData(latestRates);
          //  ratesApiResponse.setStatus(new Status(true, null, null));
            model.addAttribute("latestRates", latestRates);
        } catch (RestException e) {
            ratesApiResponse.setData(null);
            ratesApiResponse.setStatus(e.getStatus());
        }
        return SHOW_RATES;
    }
}
