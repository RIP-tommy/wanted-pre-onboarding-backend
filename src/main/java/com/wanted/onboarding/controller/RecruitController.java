package com.wanted.onboarding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.onboarding.RecruitDetailResponse;
import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.repository.RecruitRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruits")
public class RecruitController {

    @Autowired
    private RecruitRepository recruitRepository;

    @GetMapping
    public ResponseEntity<String> getRecruitList() {
        List<Tuple> recruitList = recruitRepository.findAllDetailsNotContaining();
        if (recruitList.isEmpty())
            return ResponseEntity.status(404).body("There's no Recruit.");
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Tuple tuple : recruitList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", tuple.get(0));
            resultMap.put("회사명", tuple.get(1));
            resultMap.put("채용포지션", tuple.get(2));
            resultMap.put("채용보상금", tuple.get(3));
            resultMap.put("사용기술", tuple.get(4));
            resultList.add(resultMap);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultList);
            return ResponseEntity.status(200).body(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while converting to JSON");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getRecruit(@PathVariable Long id) {
        Recruit recruit = recruitRepository.findById(id).orElse(null);

        if (recruit == null) {
            return ResponseEntity.status(404).body("There's no Recruit on id " + id);
        }

        List<Long> recruitIds = new ArrayList<>(recruitRepository.findIdsByCompanyId(recruit.getCompanyId(), recruit.getId()));

        RecruitDetailResponse response = new RecruitDetailResponse();

        response.setId(recruit.getId());
        response.setCompanyName(recruit.getCompanyName());
        response.setPosition(recruit.getPosition());
        response.setCompensation(recruit.getCompensation());
        response.setSkill(recruit.getSkill());
        response.setDetails(recruit.getDetails());
        response.setRecruitList(recruitIds);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(response);
            return ResponseEntity.status(200).body(json);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("There' an error on json processing.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchRecruits(@RequestParam String keyword) {
        List<Recruit> searchResults = recruitRepository.findByPositionContainingOrSkillContainingOrDetailsContaining(keyword, keyword, keyword);
        if (searchResults.isEmpty())
            return ResponseEntity.status(404).body("There's no matching recruit.");
        else {
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Recruit recruit : searchResults) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("id", recruit.getId());
                resultMap.put("회사명", recruit.getCompanyName());
                resultMap.put("채용포지션", recruit.getPosition());
                resultMap.put("채용보상금", recruit.getCompensation());
                resultMap.put("사용기술", recruit.getSkill());
                resultList.add(resultMap);
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(resultList);
                return ResponseEntity.status(200).body(json);
            } catch (JsonProcessingException e) {
                return ResponseEntity.internalServerError().body("There' an error on json processing.");
            }
        }
    }

    // POST 요청을 통해 채용 정보 생성
    @PostMapping
    public ResponseEntity<String> createRecruit(@RequestBody Recruit recruit) {
        try {
            return ResponseEntity.status(201).body(recruitRepository.save(recruit).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Check request body.");
        }
    }

    // PUT 요청을 통해 채용 정보 업데이트 (company_id를 제외한 모든 칼럼)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecruit(@PathVariable Long id, @RequestBody Recruit updatedRecruit) {
        Recruit existingRecruit = recruitRepository.findById(id).orElse(null);
        if (existingRecruit != null) {
            if (updatedRecruit.getId() != null) {
                return ResponseEntity.badRequest().body("Don't send ID.");
            }
            if (updatedRecruit.getCompanyId() != null) {
                return ResponseEntity.badRequest().body("Don't send company ID.");
            }
            if (updatedRecruit.getCompanyName() != null) {
                return ResponseEntity.badRequest().body("Don't send company name.");
            }
            if (updatedRecruit.getPosition() != null) {
                existingRecruit.setPosition(updatedRecruit.getPosition());
            }
            if (updatedRecruit.getCompensation() != null) {
                existingRecruit.setCompensation(updatedRecruit.getCompensation());
            }
            if (updatedRecruit.getSkill() != null) {
                existingRecruit.setSkill(updatedRecruit.getSkill());
            }
            if (updatedRecruit.getDetails() != null) {
                existingRecruit.setDetails(updatedRecruit.getDetails());
            }
            if (updatedRecruit.getPosition() == null && updatedRecruit.getCompensation() == null && updatedRecruit.getSkill() == null && updatedRecruit.getDetails() == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(recruitRepository.save(existingRecruit).toString());
        } else {
            return ResponseEntity.status(404).body("Invalid ID.");
        }
    }

    // DELETE 요청을 통해 채용 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecruit(@PathVariable Long id) {
        if (recruitRepository.findById(id).orElse(null) != null) {
            recruitRepository.deleteById(id);
            return ResponseEntity.ok("Successfully Deleted!");
        } else {
            return ResponseEntity.status(404).body(id + " recruit doesn't exist!");
        }
    }
}
