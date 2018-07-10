package com.stratio.aip.integration;

import com.stratio.aip.MicroCiTestApplication;
import com.stratio.aip.utils.HeaderSettingRequestCallback;
import com.stratio.aip.utils.ResponseResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = MicroCiTestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public abstract class AbstractIntegrationTest {

    protected static ResponseResults latestResponse = null;

    @Value("${remote.service.it.url}")
    protected String serviceUrl;

    @Autowired
    protected RestTemplate restTemplate;

    protected void executeGet(String path) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(serviceUrl + path, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return errorHandler.getResults();
            } else {
                return new ResponseResults(response);
            }
        });
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response);
        }
    }
}

