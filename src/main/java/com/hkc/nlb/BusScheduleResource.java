package com.hkc.nlb;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
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

    NLBApiService NLBApi;

    @Inject
    BusScheduleResource(
            @RestClient NLBApiService nlbService) {
        this.NLBApi = nlbService;
    }

    /**
     * list all routes
     * 
     * @return all available routes
     */
    @GET
    public Uni<Set<Route>> all() {
        return NLBApi.routes();

    }

    /**
     * search for bus routes by name containing a text
     * 
     * @param query search query must not be blank
     * @return query result containing matching bus routes
     */
    @GET
    @Path("search")
    public Uni<Set<Route>> search(@QueryParam("q") Optional<String> query) {

        if (query.isEmpty() || query.get().isBlank())         {
            throw new BadRequestException("must provide a query");
        }

        // if (Optional.ofNullable(query).empty().or)
        final Predicate<Route> routeFilter = route -> route.names.english.toLowerCase().contains(query.get().toLowerCase());
        final Comparator<Route> idComparator = (route1, route2) -> route1.id.compareTo(route2.id);

        return NLBApi.routes()
                .onItem()
                .transform(
                    // preserve order to simplify test
                        (item) -> new LinkedHashSet<>(
                                item.stream()
                                        .filter(routeFilter)
                                        .sorted(idComparator)
                                        .collect(Collectors.toList())));

    }
}