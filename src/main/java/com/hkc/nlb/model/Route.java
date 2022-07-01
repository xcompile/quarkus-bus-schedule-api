package com.hkc.nlb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Route
 * 
 * bus route details
 */
public class Route {
    
    @JsonProperty("routeId")
    public String id;

    @JsonUnwrapped
    @JsonProperty("names")
    public RouteNames names;

    @JsonProperty("specialRoute")
    public Boolean isSpecial; 
}
