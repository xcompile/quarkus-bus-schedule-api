package com.hkc.nlb;

import static io.restassured.RestAssured.given;

/**
 * BusRouteResourceTest
 */

import org.junit.jupiter.api.Test;

import com.hkc.nlb.BusScheduleResource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
@QuarkusTest
@TestHTTPEndpoint(BusScheduleResource.class)
public class BusRouteResourceTest {

    @Test
    void requestingAListOfBusRoutes() {
        given().when()
        .get("/")
        .then()
        .statusCode(200);
        // .body("$.size()",greaterThan(0));    
    }
    
}