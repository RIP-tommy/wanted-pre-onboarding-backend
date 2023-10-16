package com.wanted.onboarding.repository;

import com.wanted.onboarding.entity.Apply;
import com.wanted.onboarding.entity.ApplyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, ApplyId> {
    boolean existsByUserIdAndRecruitId(int userId, int recruitId);
}
