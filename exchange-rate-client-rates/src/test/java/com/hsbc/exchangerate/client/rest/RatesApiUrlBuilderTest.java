package com.hsbc.exchangerate.client.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
@RunWith(MockitoJUnitRunner.class)
public class RatesApiUrlBuilderTest {
    private static final String BASE_URL = "https://www.test.url";
    public static final String HISTORIC_DATE = "2020-02-10";
    public static final String BASE_CURRENCY = "EUR";
    public static final String SYMBOLS = "GBP";

    private String expectedGetLatestRatesBaseUrl;
    private String expectedGetHistoricRatesBaseUrl;

    @InjectMocks
    private RatesApiUrlBuilder underTest;
    @Before
    public void setup() throws URISyntaxException {
        ReflectionTestUtils.setField(underTest, "serviceUrl", BASE_URL);
        expectedGetLatestRatesBaseUrl = BASE_URL;
        expectedGetHistoricRatesBaseUrl = BASE_URL + "/2020-02-10";
    }

    @Test
    public void forGetLatestRates_should_returnCorrectUrl() throws Exception {

        // WHEN
        URI getLatestRates = underTest.forGetLatestRates();

        // THEN
        assertUrl(expectedGetLatestRatesBaseUrl + RatesApiUrlBuilder.GET_LATEST_URL, getLatestRates);

    }

    @Test
    public void forGetHistoricRates_should_returnCorrectUrl() throws Exception {

        // GIVEN
        Set<NameValuePair> expected = createBasicExpectedQuerySet();

        // WHEN
        URI getLatestRates = underTest.forGetHistoricalRates(HISTORIC_DATE, BASE_CURRENCY, SYMBOLS);

        // THEN
        List<NameValuePair> parsedQuery = URLEncodedUtils.parse(getLatestRates, "UTF-8");
        Set<NameValuePair> actual = parsedQuery.stream().collect(Collectors.toSet());
        assertEquals(expected, actual);
        assertUrl(expectedGetHistoricRatesBaseUrl , getLatestRates);

    }

    private Set<NameValuePair> createBasicExpectedQuerySet() {
        Set<NameValuePair> expected = new HashSet<>();
        expected.add(new BasicNameValuePair("base", "EUR"));
        expected.add(new BasicNameValuePair("symbols", "GBP"));
        return expected;
    }

    private void assertUrl(String expectedBaseUrlPath, URI uri)  {
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getScheme());
        sb.append("://");
        sb.append(uri.getHost());
        sb.append(uri.getPath());

        assertEquals(expectedBaseUrlPath, sb.toString());
    }
}