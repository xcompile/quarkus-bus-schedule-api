package com.hkc.nlb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RouteNames
 * 
 * route names in different languages
 */
public class RouteNames {
    @JsonProperty("routeName_e")
    public String english;
    @JsonProperty("routeName_c")
    public String chinese;

}
