package com.sagarvarule.support;

import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class ScenarioContext {

    private String responseCode;
    private Response response;

    private final Map<String, Object> data = new HashMap<>();

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object val = data.get(key);
        return type.isInstance(val) ? type.cast(val) : null;
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public Object remove(String key) {
        return data.remove(key);
    }

    public void clear() {
        data.clear();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
