package com.wanted.onboarding.repository;

import com.wanted.onboarding.RecruitDTO;
import com.wanted.onboarding.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
