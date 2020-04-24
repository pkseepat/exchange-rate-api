package com.hsbc.exchangerate.connection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.mock.http.client.MockClientHttpRequest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

public class HeaderInterceptorTest {
    public static final int DUMMY_BODY_SIZE = 10;
    @InjectMocks
    private HeaderInterceptor underTest;

    @Mock
    private ClientHttpRequestExecution execution;

    @Captor
    private ArgumentCaptor<HttpRequestWrapper> requestWrapperCaptor;
    @Test
    public void intercept_should_setNecessaryRequestHeaders() throws Exception {
        // GIVEN
        HttpRequest request = new MockClientHttpRequest();
        HttpRequestWrapper expected = new HttpRequestWrapper(request);
        expected.getHeaders().set("Content-Type", "application/json; charset=UTF-8");
        expected.getHeaders().set("Accept", "application/json");

        byte[] body = new byte[DUMMY_BODY_SIZE];

        given(execution.execute(requestWrapperCaptor.capture(), eq(body))).willReturn(null);

        // WHEN
        underTest.intercept(request, body, execution);

        // THEN
        HttpRequestWrapper result = requestWrapperCaptor.getValue();
        assertTrue(EqualsBuilder.reflectionEquals(expected, result));
    }
}