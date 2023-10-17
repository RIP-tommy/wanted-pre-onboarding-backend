package com.wanted.onboarding.controller;

import com.wanted.onboarding.Utils.CustomResponseEntity;
import com.wanted.onboarding.entity.Recruit;
import com.wanted.onboarding.repository.RecruitRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/recruits")
@CrossOrigin(origins = "http://localhost:3000")
public class RecruitController {

    @Autowired
    private RecruitRepository recruitRepository;

    @GetMapping
    public ResponseEntity<String> getRecruitList() {
        List<Tuple> recruitList = recruitRepository.findAllDetailsNotContaining();
        if (recruitList.isEmpty())
            return ResponseEntity.status(404).body("There's no Recruit.");
        List<String> response = new ArrayList<>();

        for (Tuple tuple : recruitList) {
            String recruitString = String.format("""
                            {
                                "id": %d,
                                "company_name" : "%s",
                                "position": "%s",
                                "compensation": "%s",
                                "skill": "%s"
                            }
                            """
                    , tuple.get(0)
                    , tuple.get(1)
                    , tuple.get(2)
                    , tuple.get(3)
                    , tuple.get(4));
            response.add(recruitString);
        }
        return CustomResponseEntity.jsonResponse(200, response.toString());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getRecruit(@PathVariable Long id) {
        Recruit recruit = recruitRepository.findById(id).orElse(null);

        if (recruit == null) {
            return ResponseEntity.status(404).body("There's no Recruit on id " + id);
        }

        List<Long> recruitIds = new ArrayList<>(recruitRepository.findIdsByCompanyId(recruit.getCompanyId(), recruit.getId()));

        String response = String.format("""
                        {
                            "id": %d,
                            "company_name" : "%s",
                            "position": "%s",
                            "compensation": %d,
                            "skill": "%s",
                            "details": "%s",
                            "company_recruit_list": %s
                        }
                        """
                , recruit.getId()
                , recruit.getCompanyName()
                , recruit.getPosition()
                , recruit.getCompensation()
                , recruit.getSkill()
                , recruit.getDetails()
                , recruitIds);

        return CustomResponseEntity.jsonResponse(200, response);
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchRecruits(@RequestParam String keyword) {
        List<Recruit> searchResults = recruitRepository.findByPositionContainingOrSkillContainingOrDetailsContaining(keyword, keyword, keyword);
        if (searchResults.isEmpty())
            return ResponseEntity.status(404).body("There's no matching recruit.");
        List<String> json = new ArrayList<>();

        for (Recruit recruit : searchResults) {
            String recruitString = String.format("""
                            {
                                "id": %d,
                                "company_name" : "%s",
                                "position": "%s",
                                "compensation": %d,
                                "skill": "%s"
                            }
                            """
                    , recruit.getId()
                    , recruit.getCompanyName()
                    , recruit.getPosition()
                    , recruit.getCompensation()
                    , recruit.getSkill());
            json.add(recruitString);
        }

        return CustomResponseEntity.jsonResponse(200, json.toString());
    }

    // POST 요청을 통해 채용 정보 생성
    @PostMapping
    public ResponseEntity<String> createRecruit(@RequestBody Recruit recruit) {
        if (recruit.getCompanyId() == null
                && recruit.getCompanyName() == null
                && recruit.getPosition() == null
                && recruit.getCompensation() == null
                && recruit.getSkill() == null
                && recruit.getDetails() == null)
            return ResponseEntity.status(204).body("");
        try {
            Recruit savedRecruit = recruitRepository.save(recruit);

            String response = String.format("""
                            {
                                "id": %d,
                                "company_id": %d,
                                "company_name" : "%s",
                                "position": "%s",
                                "compensation": "%s",
                                "skill": "%s",
                                "details": "%s",
                            }
                            """
                    , savedRecruit.getId()
                    , savedRecruit.getCompanyId()
                    , savedRecruit.getCompanyName()
                    , savedRecruit.getPosition()
                    , savedRecruit.getCompensation()
                    , savedRecruit.getSkill()
                    , savedRecruit.getDetails());
            return CustomResponseEntity.jsonResponse(201, response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Check request body.");
        }
    }

    // PUT 요청을 통해 채용 정보 업데이트 (company_id를 제외한 모든 칼럼)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecruit(@PathVariable Long id, @RequestBody Recruit request) {
        Recruit existingRecruit = recruitRepository.findById(id).orElse(null);
        if (existingRecruit != null) {
            if (request.getId() != null) {
                return ResponseEntity.badRequest().body("Don't send ID.");
            }
            if (request.getCompanyId() != null) {
                return ResponseEntity.badRequest().body("Don't send company ID.");
            }
            if (request.getCompanyName() != null) {
                return ResponseEntity.badRequest().body("Don't send company name.");
            }
            if (request.getPosition() == null && request.getCompensation() == null && request.getSkill() == null && request.getDetails() == null) {
                return ResponseEntity.noContent().build();
            }
            if (request.getPosition() != null) {
                existingRecruit.setPosition(request.getPosition());
            }
            if (request.getCompensation() != null) {
                existingRecruit.setCompensation(request.getCompensation());
            }
            if (request.getSkill() != null) {
                existingRecruit.setSkill(request.getSkill());
            }
            if (request.getDetails() != null) {
                existingRecruit.setDetails(request.getDetails());
            }
            try {
                Recruit updatedRecruit = recruitRepository.save(existingRecruit);
                StringJoiner jsonJoiner = new StringJoiner(", ");
                jsonJoiner.add(String.format("""
                        "id": %d
                        """, id));
                if (request.getPosition() != null)
                    jsonJoiner.add(String.format("""
                            "position": "%s"
                            """, updatedRecruit.getPosition()));
                if (request.getCompensation() != null)
                    jsonJoiner.add(String.format("""
                            "compensation": %d
                            """, updatedRecruit.getCompensation()));
                if (request.getSkill() != null)
                    jsonJoiner.add(String.format("""
                            "skill": "%s"
                            """, updatedRecruit.getSkill()));
                if (request.getDetails() != null)
                    jsonJoiner.add(String.format("""
                            "details": "%s"
                            """, updatedRecruit.getDetails()));
                String json = '{' + jsonJoiner.toString() + '}';
                return CustomResponseEntity.jsonResponse(200, json);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Internal Server Error");
            }
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
