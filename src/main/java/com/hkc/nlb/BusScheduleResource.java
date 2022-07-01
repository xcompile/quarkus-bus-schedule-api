package com.hkc.nlb;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hkc.nlb.model.Route;
import com.hkc.nlb.remote.NLBApiService;

/**
 * BusScheduleResource
 * 
 * API exposing bus schedule details
 */
@Path("routes")
public class BusScheduleResource {

    NLBApiService nlbService;

    @Inject
    BusScheduleResource(
        @RestClient NLBApiService nlbService
    ) {
        this.nlbService = nlbService;
    }

    @GET
    public CompletionStage<Set<Route>> all() {
        return nlbService.routes();

    }
}