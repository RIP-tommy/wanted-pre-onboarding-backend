package com.wanted.onboarding.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateCompany() throws Exception {
        String json = "{\"name\": \"카카오\"}";

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        resultActions.andExpect(status().isCreated());
    }
}
