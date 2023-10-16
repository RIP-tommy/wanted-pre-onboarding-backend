package com.wanted.onboarding.repository;

import com.wanted.onboarding.entity.Recruit;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @Query("SELECT r.id FROM Recruit r WHERE r.companyId = :companyId and r.id != :id")
    List<Long> findIdsByCompanyId(Integer companyId, Long id);
    @Query("SELECT r.id, r.companyName, r.position, r.compensation, r.skill FROM Recruit r")
    List<Tuple> findAllDetailsNotContaining();
}
