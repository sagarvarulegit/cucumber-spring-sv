package com.sagarvarule.services;

import com.sagarvarule.http.RestClient;
import io.restassured.response.Response;

public abstract class BaseGetService<R> implements ApiService<R> {
    protected final RestClient restClient;

    public BaseGetService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public final R execute() {

        String path = getPath();

        Response response = restClient.get(path);

        return processResponse(response);
    }

    protected abstract R processResponse(Response response);
}
