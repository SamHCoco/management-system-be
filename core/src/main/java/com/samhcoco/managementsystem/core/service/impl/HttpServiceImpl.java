package com.samhcoco.managementsystem.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samhcoco.managementsystem.core.model.Error;
import com.samhcoco.managementsystem.core.service.HttpService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpServiceImpl implements HttpService {

    private static final String HTTP_SERVICE = "HttpService";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Override
    public <T> Object get(@NonNull String url,
                          @NonNull Class<T> responseType,
                          Map<String, String> headers) {
        return get(url, responseType, headers, 0);
    }

    @Override
    public <T> Object get(@NonNull String url,
                          @NonNull Class<T> responseType,
                          Map<String, String> headers,
                          int retries) {

        if (retries < 0) throw new IllegalArgumentException("Http request retries must be >= 0");

        String response;
        String errorMessage = null;

        final Error error = Error.builder().errors(new HashMap<>()).build();

        int attempts = 0;
        while (attempts <= retries) {
            try {
                response = getRequest(url, headers);
                return objectMapper.readValue(response, responseType);
            } catch (RestClientException | JsonProcessingException e) {
                attempts++;
                errorMessage = String.format("HttpService.get() for URL '%s' failed - ATTEMPT %s of %s: %s",
                                              url, attempts, retries + 1, e.getMessage());
                log.error(errorMessage);
            }
        }
        error.getErrors().put(HTTP_SERVICE, errorMessage);
        return error;
    }

    /**
     * Executes Http GET request for given URL.
     * @param url Http URL
     * @param headers Http Headers
     * @return {@link String} response body
     */
    private String getRequest(@NonNull String url, Map<String, String> headers) {
        return restClient.get()
                         .uri(url)
                         .headers(h -> h.setAll(headers != null ? headers : Map.of()))
                         .retrieve()
                         .body(String.class);
    }
}
