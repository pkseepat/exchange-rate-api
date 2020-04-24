package com.hsbc.exchangerate.integration;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("rest-template-test")
@AutoConfigureWireMock(port = 0)
@AutoConfigureTestDatabase
public class ExchangeRatesIT extends AbstractIntegrationTest {

    public static final String LATEST_RATES_URI = "/latest";
    public static final String WIREMOCK_RESPONSE_LATEST_RATES_SUCCESS = "wiremock-response/latest-rates-success.json";
    public static final String WIREMOCK_RESPONSE_HISTORIC_RATES_SUCCESS = "wiremock-response/historic-rates-success.json";



    @Test
    public void shouldGetLatestRates() {
        givenIHaveMockedLatestRates(WIREMOCK_RESPONSE_LATEST_RATES_SUCCESS);

        given().auth()
                .basic("admin", "admin")
                .when()
                .get(LATEST_RATES_URI).
                then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldGetHistoricalRates() {
        givenIHaveMockedHistoricalRates(WIREMOCK_RESPONSE_HISTORIC_RATES_SUCCESS);

        given().auth()
                .basic("admin", "admin")
                .when()
                .param("days", 6)
                .post("")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    private void givenIHaveMockedLatestRates(String responseFileLocation) {
        stubFor(get(urlPathMatching(".*latest.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(responseFileLocation)
                ));
    }

    private void givenIHaveMockedHistoricalRates(String responseFileLocation) {
        stubFor(get(urlPathMatching(".*api.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(responseFileLocation)
                ));
    }
}
