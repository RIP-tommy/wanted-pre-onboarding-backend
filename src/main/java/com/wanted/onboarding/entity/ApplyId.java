package com.wanted.onboarding.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ApplyId implements Serializable {
    private Integer userId;
    private Integer recruitId;

    public ApplyId() {
    }

    public ApplyId(int userId, int recruitId) {
        this.userId = userId;
        this.recruitId = recruitId;
    }
}
