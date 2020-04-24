package com.hsbc.exchangerate.integration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.RestAssured;

public abstract class AbstractIntegrationTest {

    @Rule
    public TestName testNameProvider = new TestName();


    @Value(value = "${server.base}")
    private String serverBase;

    @Value(value = "${server.host}")
    private String serverHost;

    @Autowired
    private Environment environment;

    @Before
    public void initializeRestAssured() {
        RestAssured.port = Integer.parseInt(environment.getProperty("local.server.port"));
        RestAssured.basePath = serverBase;
        RestAssured.baseURI = serverHost;
        clearUsedRequestMappingsData();
    }

    private void clearUsedRequestMappingsData() {
        WireMock.resetAllRequests();
    }
}
