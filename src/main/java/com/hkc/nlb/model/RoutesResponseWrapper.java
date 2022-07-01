package com.hkc.nlb.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RoutesResponseWrapper
 * 
 * parent object, wrapping route details
 */
public class RoutesResponseWrapper {

    @JsonProperty("routes")
    public Set<Route> routes;
}