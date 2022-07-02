package com.hkc.nlb;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;

/**
 * BusRouteResourceTest
 */

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkc.nlb.model.Route;
import com.hkc.nlb.model.RoutesResponseWrapper;
import com.hkc.nlb.remote.NLBApiService;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
@QuarkusTest
@TestHTTPEndpoint(BusScheduleResource.class)
public class BusScheduleTest {

    @Inject
    ObjectMapper om;

    @InjectMock
    @RestClient
    NLBApiService api;
    
    @BeforeEach
    public void setup() {
        
        CompletionStage<Set<Route>> cs = CompletableFuture.<Set<Route>>supplyAsync(() -> loadSampleData());
        Mockito.when(api.routes()).thenReturn(cs);
    }
    private Set<Route> loadSampleData() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("route.snapshot.json");
        try {
            var wrapper = om.readValue(is, RoutesResponseWrapper.class);
            return wrapper.routes;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void requestingAListOfBusRoutes() {
        given().when()
        .get("/")
        .then()
        .statusCode(200);
        // .body("$.size()",greaterThan(0));    
    }



    
}