package com.wanted.onboarding.controller;

import com.wanted.onboarding.Utils.CustomResponseEntity;
import com.wanted.onboarding.entity.Apply;
import com.wanted.onboarding.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyRepository applyRepository;

    @PostMapping
    public ResponseEntity<String> apply(@RequestBody Apply apply) {
        if (apply.getUserId() == null && apply.getRecruitId() == null)
            return ResponseEntity.status(204).body("");
        if (apply.getUserId() == null || apply.getRecruitId() == null)
            return ResponseEntity.badRequest().body("Check request body.");
        boolean isDuplicate = applyRepository.existsByUserIdAndRecruitId(apply.getUserId(), apply.getRecruitId());
        if (isDuplicate)
            return ResponseEntity.badRequest().body("Duplicated applying.");
        try {
            Apply result = applyRepository.save(apply);
            String json = String.format("""
                            {
                                "user_id": %d,
                                "recruit_id": %d
                            }
                            """,
                    result.getUserId(),
                    result.getRecruitId()
            );
            return CustomResponseEntity.jsonResponse(201, json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error.");
        }
    }
}
