package com.wanted.onboarding.controller;

import com.wanted.onboarding.entity.Company;
import com.wanted.onboarding.entity.User;
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
        try {
            return ResponseEntity.status(201).body(companyRepository.save(company).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Check request body.");
        }
    }
}
