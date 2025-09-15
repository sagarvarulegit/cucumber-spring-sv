package com.sagarvarule.utils;

/**
 * Represents the response from AI (Gemini) with parsed result and failure reason
 */
public class AIResponse {
    private final String fullResponse;
    private final boolean isSuccess;
    private final String failureReason;

    public AIResponse(String fullResponse) {
        this.fullResponse = fullResponse.trim();
        this.isSuccess = parseSuccess();
        this.failureReason = extractFailureReason();
    }

    private boolean parseSuccess() {
        // Check if response starts with "Yes" (case insensitive)
        return fullResponse.toLowerCase().startsWith("yes");
    }

    private String extractFailureReason() {
        if (isSuccess) {
            return null;
        }
        
        // If response starts with "No", extract everything after "No"
        if (fullResponse.toLowerCase().startsWith("no")) {
            String reason = fullResponse.substring(2).trim();
            
            // Remove common separators like ".", ",", ":", "-" at the beginning
            if (reason.startsWith(".") || reason.startsWith(",") || 
                reason.startsWith(":") || reason.startsWith("-")) {
                reason = reason.substring(1).trim();
            }
            
            return reason.isEmpty() ? "No specific reason provided" : reason;
        }
        
        // If response doesn't start with "No" but is not success, return full response
        return fullResponse.isEmpty() ? "No response from AI" : fullResponse;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getFullResponse() {
        return fullResponse;
    }

    /**
     * Get formatted failure message for test assertions
     */
    public String getFailureMessage(String elements) {
        if (isSuccess) {
            return null;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("AI Visual Test Failed - Elements not visible: ").append(elements);
        
        if (failureReason != null && !failureReason.isEmpty()) {
            message.append("\nAI Analysis: ").append(failureReason);
        }
        
        message.append("\nFull AI Response: ").append(fullResponse);
        
        return message.toString();
    }
}
