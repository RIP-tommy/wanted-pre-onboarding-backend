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
    private Integer company_id;
    private String company_name;
    private String position;
    private Integer compensation;
    private String skill;
    private String details;
}
