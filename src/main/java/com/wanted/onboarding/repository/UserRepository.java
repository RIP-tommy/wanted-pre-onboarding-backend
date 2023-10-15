package com.wanted.onboarding.repository;

import com.wanted.onboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
