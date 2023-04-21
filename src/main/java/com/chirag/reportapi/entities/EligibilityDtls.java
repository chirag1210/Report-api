package com.chirag.reportapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "eligibility_details")
public class EligibilityDtls {

    @Id
    @GeneratedValue
    private Integer eligId;
    private String name;
    private Long mobileNo;
    private String email;
    private String gender;
    private String ssn;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private LocalDate updatedDate;
    private LocalDate createdDate;
    private String createdBy;
    private String updatedBy;
}
