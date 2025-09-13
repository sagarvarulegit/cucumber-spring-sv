package com.sagarvarule.services;

public interface ApiService<R> {
    R execute();

    String getPath();
}
