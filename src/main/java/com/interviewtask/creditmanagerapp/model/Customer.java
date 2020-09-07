package com.interviewtask.creditmanagerapp.model;

public class Customer extends SimpleCustomer{
    private CreditResult creditResult;
    private Integer creditAmount;

    public Customer(){

    }

    public Customer(SimpleCustomer sc) {
        super(sc.getFirstName(),
                sc.getLastName(),
                sc.getNationalIdNo(),
                sc.getMonthlyIncome(),
                sc.getTelephoneNumber());
        this.creditResult = CreditResult.PENDING;
        this.creditAmount = 0;
    }

    public CreditResult getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(CreditResult creditResult) {
        this.creditResult = creditResult;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }
}
