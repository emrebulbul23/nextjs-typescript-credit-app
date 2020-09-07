package com.interviewtask.creditmanagerapp.model;

import org.springframework.data.annotation.Id;

public class CreditScore {
    @Id
    private String nationalIdNo;
    private Integer customerCreditScore;

    private CreditScore(){

    }

    public CreditScore(String nationalIdNo, Integer creditScore) {
        this.nationalIdNo = nationalIdNo;
        this.customerCreditScore = creditScore;
    }

    public String getNationalIdNo() {
        return nationalIdNo;
    }

    public void setNationalIdNo(String nationalIdNo) {
        this.nationalIdNo = nationalIdNo;
    }

    public Integer getCreditScore() {
        return customerCreditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.customerCreditScore = creditScore;
    }
}
