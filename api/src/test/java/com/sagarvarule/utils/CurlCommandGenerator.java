package com.sagarvarule.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class CurlCommandGenerator {
    
    public static String generateCurlCommand(String method, String url, Map<String, Object> queryParams, 
                                           Map<String, String> headers, String body) {
        StringBuilder curl = new StringBuilder("curl");
        
        // Add method
        if (!"GET".equalsIgnoreCase(method)) {
            curl.append(" -X ").append(method.toUpperCase());
        }
        
        // Add headers
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                curl.append(" -H '").append(header.getKey()).append(": ").append(header.getValue()).append("'");
            }
        }
        
        // Add body for POST/PUT requests
        if (body != null && !body.trim().isEmpty()) {
            curl.append(" -d '").append(body.replace("'", "\\'")).append("'");
        }
        
        // Build URL with query parameters
        String fullUrl = buildUrlWithQueryParams(url, queryParams);
        curl.append(" '").append(fullUrl).append("'");
        
        return curl.toString();
    }
    
    public static String generateGetCurlCommand(String url, Map<String, Object> queryParams) {
        return generateCurlCommand("GET", url, queryParams, 
            Map.of("Accept", "application/json"), null);
    }
    
    private static String buildUrlWithQueryParams(String baseUrl, Map<String, Object> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return baseUrl;
        }
        
        String queryString = queryParams.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
            
        return baseUrl + "?" + queryString;
    }
}
