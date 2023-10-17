package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.Apply;
import com.wanted.onboarding.repository.ApplyRepository;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
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
public class ApplyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplyRepository applyRepository;

    @Test
    public void testApply() throws Exception {
        int userId = 3;
        int recruitId = 2;

        Apply apply = new Apply();
        apply.setUserId(userId);
        apply.setRecruitId(recruitId);

        mockMvc.perform(MockMvcRequestBuilders.post("/apply") // POST 요청
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(apply)))
                .andExpect(status().isCreated()) // HTTP 상태 코드 201인지 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Content-Type 헤더가 application/json인지 확인

        // 생성된 지원 내역 저장 여부 확인
        Assertions.assertTrue(applyRepository.existsByUserIdAndRecruitId(userId, recruitId));
    }
}
