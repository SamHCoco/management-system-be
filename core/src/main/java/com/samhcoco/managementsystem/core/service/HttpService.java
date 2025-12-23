package com.samhcoco.managementsystem.core.service;

import java.util.Map;

public interface HttpService {

    /**
     * Execute Http GET request.
     * @param url {@link String} URL
     * @param responseType {@link T} Response Type
     * @param headers {@link Map} Http headers
     * @return {@link Object} Http response
     * @param <T> {@link T} Response type
     */
    <T> Object get(String url, Class<T> responseType, Map<String, String> headers);

    /**
     * Execute Http GET request, with retries on failure.
     * @param url {@link String} URL
     * @param responseType {@link T} Response Type
     * @param headers {@link Map} Http headers
     * @param retries Number of retries on request failure
     * @return {@link Object} Http response
     * @param <T> {@link T} Response type
     */
    <T> Object get(String url, Class<T> responseType, Map<String, String> headers, int retries);

}
