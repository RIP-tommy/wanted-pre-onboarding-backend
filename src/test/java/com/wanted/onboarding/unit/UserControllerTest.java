package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.User;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setName("John Doe");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users") // POST 요청
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(user)))
                .andExpect(status().isCreated()) // HTTP 상태 코드 201인지 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Content-Type 헤더가 application/json인지 확인
    }
}
