package com.wanted.onboarding.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "company_id", nullable = false)
    private Integer companyId;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    private String position;
    private Integer compensation;
    private String skill;
    private String details;
}
