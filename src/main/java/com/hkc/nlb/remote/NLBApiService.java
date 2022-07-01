package com.hkc.nlb.remote;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.hkc.nlb.model.Route;

/**
 * NLBApiService
 * 
 * delegating to NLB api
 */
@RegisterRestClient
@RegisterProvider(RouteMessageBodyReader.class)
@Path("/")
public interface NLBApiService {

    @GET
    CompletionStage<Set<Route>> routes();
    
    
}