package com.interviewtask.creditmanagerapp.controllers;

import com.interviewtask.creditmanagerapp.model.CreditScore;
import com.interviewtask.creditmanagerapp.respositories.ICreditScoreRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("creditScore")
public class CreditScoreController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ICreditScoreRepository creditScoreRepository;

    public CreditScoreController(ICreditScoreRepository creditScoreRepository){
        this.creditScoreRepository = creditScoreRepository;
    }

    /**
     * Get a customer's credit score with their national id number.
     * @param id Customer's national id number.
     * @return CreditScore object of the customer.
     */
    @Operation(summary = "Get a customer's credit score.")
    @GetMapping(value="")
    public CreditScore getCreditScoreWithNationalId(String id){
        Optional<CreditScore> byId = creditScoreRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CreditScore is not found");
        }
        return byId.get();
    }

    /**
     * Get a customer's credit score with their national id number.
     * @param id Customer's national id number.
     * @return CreditScore object of the customer.
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "Get a customer's credit score.")
    @GetMapping(value="get")
    public String get(String id){
        return "hello emre";
    }

    /**
     * Adds a customers credit score to the collection using their national
     * id number. If national id number exists in db, it is updated.
     * @param cs CreditScore of customer.
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "Add or update a customers credit score.")
    @PostMapping(value="")
    public ResponseEntity<String> putCreditScore(@RequestBody CreditScore cs){
        Optional<CreditScore> byId = creditScoreRepository.findById(cs.getNationalIdNo());
        creditScoreRepository.save(cs);
        if (byId.isEmpty()) {
            return ResponseEntity.ok("New Credit Score is added!");
        }
        return ResponseEntity.ok("Credit Score is updated!");
    }
}
