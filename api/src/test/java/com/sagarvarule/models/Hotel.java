package com.sagarvarule.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Hotel(
    String name,
    String city,
    @JsonProperty("available_from") String availableFrom,
    @JsonProperty("available_to") String availableTo,
    @JsonProperty("image_url") String imageUrl
) {}
