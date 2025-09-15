package com.sagarvarule.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AIUtil {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    private static final String GEMINI_API_KEY = System.getProperty("GEMINI_API_KEY", "YOUR_API_KEY_HERE");
    
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getResponse(String base64Image, String prompt) {
        try {
            // Construct JSON payload for Gemini API
            String jsonPayload = buildGeminiPayload(base64Image, prompt);
            
            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GEMINI_API_URL))
                    .header("x-goog-api-key", GEMINI_API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .timeout(Duration.ofSeconds(60))
                    .build();
            
            // Send request and get response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() != 200) {
                throw new RuntimeException("Gemini API call failed with status: " + response.statusCode() + 
                                         ", body: " + response.body());
            }
            
            // Parse response and extract text
            return parseGeminiResponse(response.body());
            
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to call Gemini API: " + e.getMessage(), e);
        }
    }
    
    private static String buildGeminiPayload(String base64Image, String prompt) {
        try {
            // Build JSON structure matching Gemini API format
            String jsonPayload = String.format("""
                {
                    "contents": [{
                        "parts": [
                            {
                                "inline_data": {
                                    "mime_type": "image/jpeg",
                                    "data": "%s"
                                }
                            },
                            {
                                "text": "%s"
                            }
                        ]
                    }]
                }
                """, base64Image, prompt.replace("\"", "\\\""));
            
            return jsonPayload;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to build Gemini API payload: " + e.getMessage(), e);
        }
    }
    
    private static String parseGeminiResponse(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            
            // Navigate through the JSON structure to extract the text response
            JsonNode candidatesNode = rootNode.get("candidates");
            if (candidatesNode != null && candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode firstCandidate = candidatesNode.get(0);
                JsonNode contentNode = firstCandidate.get("content");
                if (contentNode != null) {
                    JsonNode partsNode = contentNode.get("parts");
                    if (partsNode != null && partsNode.isArray() && partsNode.size() > 0) {
                        JsonNode firstPart = partsNode.get(0);
                        JsonNode textNode = firstPart.get("text");
                        if (textNode != null) {
                            return textNode.asText().trim();
                        }
                    }
                }
            }
            
            throw new RuntimeException("Unable to parse text from Gemini response: " + responseBody);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini API response: " + e.getMessage(), e);
        }
    }
}
