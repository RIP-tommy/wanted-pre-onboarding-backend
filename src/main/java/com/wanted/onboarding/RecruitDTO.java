package com.wanted.onboarding;

import lombok.Data;

@Data
public class RecruitDTO {
    private Long id;
    private String companyName;
    private String position;
    private int compensation;
    private String skill;

    public RecruitDTO(Long id, String companyName, String position, int compensation, String skill) {
        this.id = id;
        this.companyName = companyName;
        this.position = position;
        this.compensation = compensation;
        this.skill = skill;
    }
}
