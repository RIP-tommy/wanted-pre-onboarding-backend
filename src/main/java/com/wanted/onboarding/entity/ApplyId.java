package com.wanted.onboarding.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ApplyId implements Serializable {
    private int userId;
    private int recruitId;

    public ApplyId() {
    }

    public ApplyId(int userId, int recruitId) {
        this.userId = userId;
        this.recruitId = recruitId;
    }
}
