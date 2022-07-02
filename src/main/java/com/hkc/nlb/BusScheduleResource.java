package com.hkc.nlb;

import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hkc.nlb.model.Route;
import com.hkc.nlb.remote.NLBApiService;

import io.smallrye.mutiny.Uni;

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
            @RestClient NLBApiService nlbService) {
        this.nlbService = nlbService;
    }

    @GET
    public CompletionStage<Set<Route>> all() {
        return nlbService.routes();

    }

    @GET
    @Path("search")
    public Uni<Set<Route>> search(@QueryParam("q") String query) {

        
        final Predicate<Route> routeFilter = route -> route.names.english.toLowerCase().contains(query.toLowerCase());

        return Uni.createFrom()
        .completionStage(nlbService.routes())
        .onItem()
        .transform(
            (item) -> item.stream()
            .filter(routeFilter)
            .collect(Collectors.toSet())
        );
    }
}