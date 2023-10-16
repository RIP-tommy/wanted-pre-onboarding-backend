package com.wanted.onboarding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.onboarding.entity.Apply;
import com.wanted.onboarding.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyRepository applyRepository;

    @PostMapping
    public ResponseEntity<String> apply(@RequestBody Apply apply) {
        try {
            boolean isDuplicate = applyRepository.existsByUserIdAndRecruitId(apply.getUserId(), apply.getRecruitId());
            if (isDuplicate) {
                return ResponseEntity.badRequest().body("Duplicated applying.");
            } else {
                Apply result = applyRepository.save(apply);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("user_id", result.getUserId());
                resultMap.put("recruit_id", result.getRecruitId());

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(resultMap);
                    return ResponseEntity.status(201).body(json);
                } catch (JsonProcessingException e) {
                    return ResponseEntity.internalServerError().body("There' an error on json processing.");
                }
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Duplicated applying.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("There's no resource that u requested.");
        }
    }
}
