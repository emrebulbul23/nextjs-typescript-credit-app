package com.interviewtask.creditmanagerapp.response;

import com.interviewtask.creditmanagerapp.model.CreditResult;

public class CreditResultResponse {
    private String responseMessage;
    private CreditResult creditResult;
    private Integer creditAmount;

    public CreditResultResponse(String responseMessage, CreditResult creditResult, Integer creditAmount) {
        this.responseMessage = responseMessage;
        this.creditResult = creditResult;
        this.creditAmount = creditAmount;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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
