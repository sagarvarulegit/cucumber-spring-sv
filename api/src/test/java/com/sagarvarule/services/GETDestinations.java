package com.sagarvarule.services;

import org.springframework.stereotype.Component;

@Component
public class GETDestinations {
    private String path = "/api/destination";

    public String getResponse(){
            return "200";
    }
}
