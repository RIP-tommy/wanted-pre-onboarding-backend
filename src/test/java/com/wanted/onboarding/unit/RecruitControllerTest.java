package com.wanted.onboarding.unit;

import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class RecruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateRecruit() throws Exception {

        Recruit recruit = new Recruit();
        recruit.setCompany_id(3);
        recruit.setCompany_name("네이버");
        recruit.setPosition("백엔드 주니어 개발자");
        recruit.setCompensation(150000);
        recruit.setSkill("Python");
        recruit.setDetails("Lorem ipsum dolor sit amet");

        mockMvc.perform(MockMvcRequestBuilders.post("/recruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(recruit))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

//    @Test
//    public void testDeleteRecruit() throws Exception {
//        // Recruit 엔티티를 먼저 생성하여 데이터베이스에 추가합니다.
//        Recruit recruit = new Recruit();
//        recruit.setPosition("테스트 포지션");
//        recruit.setCompensation(100000);
//        recruit.setSkill("테스트 스킬");
//        recruitRepository.save(recruit);
//
//        // Recruit 삭제 요청을 보냅니다.
//        mockMvc.perform(MockMvcRequestBuilders.delete("/recruit/{id}", recruit.getId()))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // 삭제한 Recruit 엔티티가 더 이상 데이터베이스에 없는지 확인합니다.
//        assert !recruitRepository.existsById(recruit.getId());
//    }
//
//    @Test
//    public void testUpdateRecruit() throws Exception {
//        // Recruit 엔티티를 먼저 생성하여 데이터베이스에 추가합니다.
//        Recruit recruit = new Recruit();
//        recruit.setPosition("테스트 포지션");
//        recruit.setCompensation(100000);
//        recruit.setSkill("테스트 스킬");
//        recruitRepository.save(recruit);
//
//        // 업데이트할 Recruit 데이터를 생성합니다.
//        Recruit updatedRecruit = new Recruit();
//        updatedRecruit.setPosition("업데이트된 포지션");
//        updatedRecruit.setCompensation(120000);
//        updatedRecruit.setSkill("업데이트된 스킬");
//
//        // Recruit 업데이트 요청을 보냅니다.
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/recruit/{id}", recruit.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonUtils.asJsonString(updatedRecruit)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Recruit 엔티티가 업데이트된 정보를 가지고 있는지 확인합니다.
//        Recruit updatedEntity = recruitRepository.findById(recruit.getId()).orElse(null);
//        assert updatedEntity != null;
//        assertEquals(updatedRecruit.getPosition(), updatedEntity.getPosition());
//        assertEquals(updatedRecruit.getCompensation(), updatedEntity.getCompensation());
//        assertEquals(updatedRecruit.getSkill(), updatedEntity.getSkill());
//    }
}