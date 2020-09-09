package com.interviewtask.creditmanagerapp.controllers;

import com.interviewtask.creditmanagerapp.model.CreditResult;
import com.interviewtask.creditmanagerapp.model.CreditScore;
import com.interviewtask.creditmanagerapp.model.SimpleCustomer;
import com.interviewtask.creditmanagerapp.response.CreditResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Change database for test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
        "spring.data.mongodb.database=testdbc"
        })
class CreditApplicationControllerTest {
    //start on random port for test
    @LocalServerPort
    private int port;

    // rest controller
    @Autowired
    private TestRestTemplate restTemplate;

    // mongo controller
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        // clean db
        mongoTemplate.dropCollection("creditScore");
        mongoTemplate.dropCollection("customer");

    }

    @Test
    void testRejectedForLowCreditScore(){
        // add credit score objects
        CreditScore creditScore = new CreditScore("12345678", 499);
        mongoTemplate.insert(creditScore,"creditScore");

        SimpleCustomer simpleCustomer = new SimpleCustomer("emre", "b", "12345678",
                5000, "05554443322");
        //test rejected
        ResponseEntity<CreditResultResponse> creditResultResponseResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, CreditResultResponse.class);
        assertEquals("Credit application is rejected due to low credit score!",
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getResponseMessage());
        assertEquals(CreditResult.REJECTED,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditResult());
        assertEquals(0,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditAmount());
    }

    @Test
    void testRejectedForHighMonthlyIncomeAndLowCreditScore(){
        // add credit score objects
        CreditScore creditScore = new CreditScore("12345678", 999);
        mongoTemplate.insert(creditScore,"creditScore");

        SimpleCustomer simpleCustomer = new SimpleCustomer("emre", "b", "12345678",
                5000, "05554443322");
        //test rejected
        ResponseEntity<CreditResultResponse> creditResultResponseResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, CreditResultResponse.class);
        assertEquals("Credit application is rejected due to low credit score!",
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getResponseMessage());
        assertEquals(CreditResult.REJECTED,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditResult());
        assertEquals(0,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditAmount());
    }

    @Test
    void testAcceptForHighCreditScore(){
        // add credit score objects
        CreditScore creditScore = new CreditScore("12345678", 1000);
        mongoTemplate.insert(creditScore,"creditScore");

        int monthlyIncome = 5000;
        SimpleCustomer simpleCustomer = new SimpleCustomer("emre", "b", "12345678",
                monthlyIncome, "05554443322");
        //test approved
        ResponseEntity<CreditResultResponse> creditResultResponseResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, CreditResultResponse.class);
        assertEquals("Credit application is approved!",
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getResponseMessage());
        assertEquals(CreditResult.ACCEPTED,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditResult());
        assertEquals(monthlyIncome*4,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditAmount());

        //re-apply and see warning message
        creditResultResponseResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, CreditResultResponse.class);
        assertEquals("An application was made for this customer before!",
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getResponseMessage());
        assertEquals(CreditResult.ACCEPTED,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditResult());
        assertEquals(monthlyIncome*4,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditAmount());
    }

    @Test
    void testAcceptedForLowMonthlyIncomeAndLowCreditScore(){
        // add credit score objects
        CreditScore creditScore = new CreditScore("12345678", 999);
        mongoTemplate.insert(creditScore,"creditScore");

        SimpleCustomer simpleCustomer = new SimpleCustomer("emre", "b", "12345678",
                4999, "05554443322");
        //test approved
        ResponseEntity<CreditResultResponse> creditResultResponseResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, CreditResultResponse.class);
        assertEquals("Credit application is approved!",
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getResponseMessage());
        assertEquals(CreditResult.ACCEPTED,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditResult());
        assertEquals(10000,
                Objects.requireNonNull(creditResultResponseResponseEntity.getBody()).getCreditAmount());
    }

    @Test
    void testCreditScoreNotFound(){
        SimpleCustomer simpleCustomer = new SimpleCustomer("emre", "b", "12345678",
                5000, "05554443322");

        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, String.class);
        assertEquals(HttpStatus.NOT_FOUND, stringResponseEntity.getStatusCode());
        assertEquals("Credit score cannot be found for given customer", stringResponseEntity.getBody());
    }

    @Test
    void testInvalidCustomer(){
        SimpleCustomer simpleCustomer = new SimpleCustomer("", "b", "12345678",
                5000, "");

        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/creditApplication", simpleCustomer, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());
        assertEquals("Customer information contains empty or invalid fields", stringResponseEntity.getBody());
    }
}