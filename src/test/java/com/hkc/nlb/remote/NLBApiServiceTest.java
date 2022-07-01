package com.hkc.nlb.remote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * NLBApiServiceTest
 */
@QuarkusTest
public class NLBApiServiceTest {

    @ConfigProperty(name = "quarkus.rest-client.\"com.hkc.nlb.remote.NLBApiService\".url")
    Optional<String> url;

    @Test
    void verifyThatRestClientUrlIsValidAbsoluteURI() {
        assertThat("url is present", url.isPresent());
        assertThat("is absolute", URI.create(url.get()).isAbsolute(), is(true));
    }

}