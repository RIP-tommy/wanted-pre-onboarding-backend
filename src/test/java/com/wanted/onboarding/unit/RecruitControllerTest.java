package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.repository.RecruitRepository;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecruitRepository recruitRepository;

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

    @Test
    public void testUpdateRecruit() throws Exception {
        // Recruit 엔티티를 먼저 생성하여 데이터베이스에 추가합니다.
        Recruit recruit = new Recruit();
        recruit.setCompanyId(2);
        recruit.setCompanyName("wanted");
        recruit.setPosition("테스트 포지션");
        recruit.setCompensation(100000);
        recruit.setSkill("테스트 스킬");
        recruit.setDetails("테스트 설명");
        recruitRepository.save(recruit);

        // 업데이트할 Recruit 데이터를 생성합니다.
        Recruit updatedRecruit = new Recruit();
        updatedRecruit.setPosition("업데이트된 포지션");
        updatedRecruit.setCompensation(120000);
        updatedRecruit.setSkill("업데이트된 스킬");

        // Recruit 업데이트 요청을 보냅니다.

        mockMvc.perform(MockMvcRequestBuilders.put("/recruits/{id}", recruit.getId())
                        .contentType("application/json")
                        .content(JsonUtils.asJsonString(updatedRecruit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Recruit 엔티티가 업데이트된 정보를 가지고 있는지 확인합니다.
        Recruit updatedEntity = recruitRepository.findById(recruit.getId()).orElse(null);
        assert updatedEntity != null;
        assertEquals(updatedRecruit.getPosition(), updatedEntity.getPosition());
        assertEquals(updatedRecruit.getCompensation(), updatedEntity.getCompensation());
        assertEquals(updatedRecruit.getSkill(), updatedEntity.getSkill());
        assertEquals(recruit.getDetails(), updatedEntity.getDetails());
    }

    @Test
    public void testDeleteRecruit() throws Exception {
        // 새로운 채용 정보를 생성하고 저장합니다.
        Recruit recruit = new Recruit();
        recruit.setCompanyId(1);
        recruit.setCompanyName("테스트 회사");
        recruit.setPosition("테스트 포지션");
        recruit.setCompensation(100000);
        recruit.setSkill("테스트 스킬");
        recruit.setDetails("테스트 상세 정보");

        Recruit savedRecruit = recruitRepository.save(recruit);

        mockMvc.perform(MockMvcRequestBuilders.delete("/recruits/{id}", savedRecruit.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 데이터베이스에서 해당 채용 정보가 삭제되었는지 확인합니다.
        Recruit deletedRecruit = recruitRepository.findById(savedRecruit.getId()).orElse(null);
        assertNull(deletedRecruit);
    }
}
