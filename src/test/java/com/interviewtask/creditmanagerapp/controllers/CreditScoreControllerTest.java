package com.interviewtask.creditmanagerapp.controllers;

import com.interviewtask.creditmanagerapp.model.CreditResult;
import com.interviewtask.creditmanagerapp.model.CreditScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

// Change database for test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.data.mongodb.database=testdbc"
        })
class CreditScoreControllerTest {
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
    }

    @Test
    void testGetCreditScoreWithNationalId() {
        CreditScore creditScore = new CreditScore("12345678", 5000);
        mongoTemplate.insert(creditScore,"creditScore");

        CreditScore creditResponse = this.restTemplate.getForObject("http://localhost:" + port +
                "/creditScore?id=" + creditScore.getNationalIdNo(), CreditScore.class);
        assertEquals(5000, creditResponse.getCreditScore());
        assertEquals("12345678", creditResponse.getNationalIdNo());
    }

    @Test
    void testPutCreditScore() {
        // test put credit score
        CreditScore creditScore = new CreditScore("12345678", 5000);
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/creditScore", creditScore, String.class);
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("New Credit Score is added!", stringResponseEntity.getBody());
        // test update credit score
        creditScore = new CreditScore("12345678", 5000);
        ResponseEntity<String> stringResponseEntity2 = this.restTemplate.postForEntity("http://localhost:" + port +
                "/creditScore", creditScore, String.class);
        assertEquals(HttpStatus.OK, stringResponseEntity2.getStatusCode());
        assertEquals("Credit Score is updated!", stringResponseEntity2.getBody());
    }
}