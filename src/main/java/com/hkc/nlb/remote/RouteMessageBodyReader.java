package com.hkc.nlb.remote;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkc.nlb.model.Route;
import com.hkc.nlb.model.RoutesResponseWrapper;

/**
 * RouteMessageBodyReader
 */
// @Provider
@ConstrainedTo(RuntimeType.CLIENT)
public class RouteMessageBodyReader implements MessageBodyReader<Set<Route>> {

    private ObjectMapper om;

    @Inject
    public RouteMessageBodyReader(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        
        return true;
    }

    @Override
    public Set<Route> readFrom(Class<Set<Route>> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        
                var routes = om.readValue(entityStream, RoutesResponseWrapper.class);
                return routes.routes;
    }


}