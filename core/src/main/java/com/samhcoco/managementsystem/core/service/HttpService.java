package com.samhcoco.managementsystem.core.service;

import lombok.NonNull;

import java.time.Duration;
import java.util.Map;

// todo - add documentation
public interface HttpService {

    <T> Object get(String url, Class<T> responseType, Map<String, String> headers);

    <T> Object get(String url, Class<T> responseType, int retries, Duration timeout);

}
