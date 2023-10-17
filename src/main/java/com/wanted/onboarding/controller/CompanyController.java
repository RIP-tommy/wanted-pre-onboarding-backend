package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.Company;
import com.wanted.onboarding.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        if (company.getId() != null)
            return ResponseEntity.badRequest().body("Don't send id value.");
        if (company.getName() == null)
            return ResponseEntity.status(204).body("");
        try {
            Company savedCompany = companyRepository.save(company);

            Long id = savedCompany.getId();
            String name = savedCompany.getName();
            String json = String.format("{\"id\": %d, \"name\": \"%s\"}", id, name);
            return ResponseEntity.status(201).body(json);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something's going wrong on server :(");
        }
    }
}
