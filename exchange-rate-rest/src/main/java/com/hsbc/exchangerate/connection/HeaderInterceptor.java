package com.hsbc.exchangerate.connection;
/**
 * Created by pseepathi on 23/04/2020.
 */
import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

public class HeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        requestWrapper.getHeaders().set("Content-Type", "application/json; charset=UTF-8");
        requestWrapper.getHeaders().set("Accept", "application/json");
        return execution.execute(requestWrapper, body);
    }
}