package com.interviewtask.creditmanagerapp.model;

import org.springframework.data.annotation.Id;

public class SimpleCustomer {
    @Id
    protected String nationalIdNo;

    protected String firstName;
    protected String lastName;
    protected Integer monthlyIncome;
    protected String telephoneNumber;

    protected SimpleCustomer(){

    }

    public SimpleCustomer(String firstName, String lastName, String nationalIdNo, Integer monthlyIncome, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalIdNo = nationalIdNo;
        this.monthlyIncome = monthlyIncome;
        this.telephoneNumber = telephoneNumber;
    }

    public boolean validate(){
        return !(nationalIdNo.isBlank() ||
                firstName.isBlank() ||
                lastName.isBlank() ||
                monthlyIncome < 0 ||
                telephoneNumber.isBlank());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalIdNo() {
        return nationalIdNo;
    }

    public void setNationalIdNo(String nationalIdNo) {
        this.nationalIdNo = nationalIdNo;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
