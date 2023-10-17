package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.Company;
import com.wanted.onboarding.repository.CompanyRepository;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateCompany() throws Exception {
        Company company = new Company();
        company.setName("wanted");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/companies") // POST 요청
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(company)))
                .andExpect(status().isCreated()) // HTTP 상태 코드 201인지 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Content-Type 헤더가 application/json인지 확인
    }
}
