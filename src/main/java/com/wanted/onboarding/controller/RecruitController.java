package com.wanted.onboarding.controller;

import com.wanted.onboarding.RecruitDTO;
import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recruits")
public class RecruitController {

    @Autowired
    private RecruitRepository recruitRepository;

    @GetMapping
    public List<RecruitDTO> getRecruitList() {
        List<Recruit> recruits = recruitRepository.findAll();
        List<RecruitDTO> recruitDTOs = recruits.stream().map(this::convertToDTO).collect(Collectors.toList());
        return recruitDTOs;
    }

    private RecruitDTO convertToDTO(Recruit recruit) {
        return new RecruitDTO(recruit.getId(), recruit.getCompany_name(), recruit.getPosition(), recruit.getCompensation(), recruit.getSkill());
    }

    @GetMapping("/{id}")
    public Recruit getRecruit(@PathVariable Long id) {
        return recruitRepository.findById(id).orElse(null);
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
            if (updatedRecruit.getCompany_id() != null) {
                return ResponseEntity.badRequest().body("Don't send company ID.");
            }
            if (updatedRecruit.getCompany_name() != null) {
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
                existingRecruit.setSkill(updatedRecruit.getDetails());
            }
            if (updatedRecruit.getPosition() == null
                    && updatedRecruit.getCompensation() == null
                    && updatedRecruit.getSkill() == null
                    && updatedRecruit.getDetails() == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(recruitRepository.save(existingRecruit).toString());
        } else {
            return ResponseEntity.badRequest().body("Invalid ID.");
        }
    }

    // DELETE 요청을 통해 채용 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecruit(@PathVariable Long id) {
        if (recruitRepository.findById(id).orElse(null) != null) {
            recruitRepository.deleteById(id);
            return ResponseEntity.ok("Successfully Deleted!");
        } else {
            return ResponseEntity.badRequest().body(id + " recruit doesn't exist!");
        }
    }
}
