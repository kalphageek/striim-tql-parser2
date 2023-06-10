package com.skhynix.datahub.striimtqlparser2.feign;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MockCacheApi {
    public static void setupStriimApiClient_getStatus(WireMockServer mockCacheApi) {
        mockCacheApi.stubFor(WireMock.get(WireMock.urlMatching("/api/[\\w-_\\.]*/status"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("payload/striim-status.json")
                )
        );
    }
}