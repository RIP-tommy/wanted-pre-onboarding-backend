package com.wanted.onboarding;

import lombok.Data;

import java.util.List;

@Data
public class RecruitDetailResponse {
    private Long id;
    private String companyName;
    private String position;
    private Integer compensation;
    private String skill;
    private String details;
    private List<Long> recruitList;
}
