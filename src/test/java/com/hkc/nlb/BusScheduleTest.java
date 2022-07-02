package com.hkc.nlb;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.InputStream;
import java.util.Set;

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
import io.smallrye.mutiny.Uni;

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

        Uni<Set<Route>> cs = Uni.createFrom().item(loadSampleData());
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

    @Test
    void ifSearchingForRouteInEnglishAlsoPartialMatchesAreReturned() {

        given().queryParam("q", "Lo").when()
        .get("search")
        .then()
        .statusCode(200)
        .body("$.size()",is(3),
        "[0].routeId",is("37"),
        "[1].routeId",is("38"),
        "[2].routeId",is("75")
        );
        
        
    }

    @Test
    void ifSearchingForRouteWithBlankQueryReturnHTTP400() {

        
        given().queryParam("q", "").when()
        .get("search")
        .then()
        .statusCode(400);
        
        
    }
}