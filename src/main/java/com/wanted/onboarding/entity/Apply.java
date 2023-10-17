package com.wanted.onboarding.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "apply")
@IdClass(ApplyId.class)
@Data
public class Apply {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Id
    @Column(name = "recruit_id", nullable = false)
    private Integer recruitId;
}