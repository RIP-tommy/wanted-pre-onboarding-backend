package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.Apply;
import com.wanted.onboarding.repository.ApplyRepository;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        int userId = 1;
        int recruitId = 1;

        Apply apply = new Apply();
        apply.setUserId(userId);
        apply.setRecruitId(recruitId);

        mockMvc.perform(MockMvcRequestBuilders.post("/apply")
                        .contentType("application/json")
                        .content(JsonUtils.asJsonString(apply)))
                .andExpect(status().isCreated());

        Assertions.assertTrue(applyRepository.existsByUserIdAndRecruitId(userId, recruitId));
    }
}
