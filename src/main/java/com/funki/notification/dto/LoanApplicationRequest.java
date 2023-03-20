package com.test.notification.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequest {
    /*
    *   base
    */
    private String id;
    private String createdBy;
    private String modifiedBy;
    private String createdDate;
    private String modifiedDate;
    private String status;

    /*
    *   step 1
    */
    private String loanAmount;
    private String repayCycle;
    private String loanPurpose;

    /*
    *   step 3
    */
    private String salutation;
    private String engName;
    private String hkid;
    private Date dob;
    private String flat;
    private String floor;
    private String block;
    private String building;
    private String street;
    private String area;
    private String livingPeriod;
    private String nationality;
    private String mobile;
    private String email;
    private String occupation;
    private String position;
    private String monthlyIncome;
    private String tuConsent;
    private String marketingCommunicationConsent;

    /*
    *   step 4
    */
    private String companyName;
    private String companyPhone;
    private String companyFlat;
    private String companyFloor;
    private String companyBlock;
    private String companyArea;
    private String companyBuilding;
    private String companyStreet;
    private String department;
    private String jobNature;
    private Double employmentYear;
    private Double employmentMonth;
    private String otherIncome;
    private String maritalStatus;
    private String livingStatus;
    private String housingExpenses;
    private String hkidFileName;
    private String addressProofFileName;
    private String incomeProofFileName;
    private String accountProofFileName;

    /*
    *   step 7
    */
    private String personalInfoConsent;
}