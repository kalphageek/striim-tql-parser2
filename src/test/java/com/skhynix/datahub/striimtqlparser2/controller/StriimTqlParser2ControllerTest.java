package com.skhynix.datahub.striimtqlparser2.controller;

import com.skhynix.datahub.striimtqlparser2.service.proxy.StriimApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class StriimTqlParser2ControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void runMyJobTest() throws Exception {
        String appName = "aa";
        String result = "aaa";
        String jobName = "MyJob";
        StriimApiClient apiClient = mock(StriimApiClient.class);
        when(apiClient.getStatus(appName)).thenReturn(result);

        mockMvc.perform(post("/api/batch/"+jobName))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

}