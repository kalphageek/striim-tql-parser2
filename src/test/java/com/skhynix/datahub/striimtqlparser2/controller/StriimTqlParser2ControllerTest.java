package com.skhynix.datahub.striimtqlparser2.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.skhynix.datahub.striimtqlparser2.feign.MockCacheApi;
import com.skhynix.datahub.striimtqlparser2.feign.MockCacheApiConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = { MockCacheApiConfig.class })
class StriimTqlParser2ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    WireMockServer mockCacheApi;

    @Test
    public void runMyJobTest() throws Exception {
        String jobName = "MyJob";
        MockCacheApi.setupStriimApiClient_getStatus(mockCacheApi);

        mockMvc.perform(post("/api/batch/"+jobName))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("exitCode").value("COMPLETED"))
        ;
    }
}