package com.interviewtask.creditmanagerapp.model;

public class Customer {
    private String firstName;
    private String lastName;
    private String nationalIdNo;
    private Integer monthlyIncome;
    private String telephoneNumber;

    private Customer(){

    }

    public Customer(String firstName, String lastName, String nationalIdNo, Integer monthlyIncome, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalIdNo = nationalIdNo;
        this.monthlyIncome = monthlyIncome;
        this.telephoneNumber = telephoneNumber;
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
