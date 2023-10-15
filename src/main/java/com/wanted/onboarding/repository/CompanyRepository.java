package com.wanted.onboarding.repository;

import com.wanted.onboarding.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
