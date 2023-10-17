package com.wanted.onboarding.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetRecruit() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recruits/3")) // GET 요청
                .andExpect(status().isOk()) // HTTP 상태 코드가 200인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("네이버")); // JSON 응답 내용 검증
    }

    @Test
    public void testCreateRecruit() throws Exception {
        String json = "{\"companyId\": 7, \"companyName\": \"카카오\", \"position\": \"백엔드 주니어 개발자\", \"compensation\": 1500000, \"skill\": \"Java\", \"details\": \"Lorem ipsum\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/recruits")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }
}
