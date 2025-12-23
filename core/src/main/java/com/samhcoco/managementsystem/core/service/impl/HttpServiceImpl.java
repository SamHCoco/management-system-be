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

import java.time.Duration;
import java.util.Map;

import static java.util.Objects.nonNull;

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
        try {
            final String response;
            if (nonNull(headers)) {
                response = restClient.get()
                                     .uri(url)
                                     .headers(h -> h.setAll(headers))
                                     .retrieve()
                                     .body(String.class);
            } else {
                response = restClient.get()
                                     .uri(url)
                                     .retrieve()
                                     .body(String.class);
            }
            return objectMapper.readValue(response, responseType);
        } catch(RestClientException | JsonProcessingException e){
            final String errorMessage = String.format("HttpService.get() failed for URL '%s': %s", url, e.getMessage());
            final Error error = Error.builder()
                                     .errors(Map.of(HTTP_SERVICE, errorMessage))
                                     .build();

            log.error(errorMessage);
            return error;
        }
    }

    @Override
    public <T> Object get(String url, Class<T> responseType, int retries, Duration timeout) {
        return null;
    }
}
