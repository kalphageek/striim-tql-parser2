package com.skhynix.datahub.striimtqlparser2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
        mockMvc.perform(post("/api/batch/MyJob"))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

}