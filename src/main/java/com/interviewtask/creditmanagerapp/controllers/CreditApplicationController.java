package com.interviewtask.creditmanagerapp.controllers;

import com.interviewtask.creditmanagerapp.model.CreditResult;
import com.interviewtask.creditmanagerapp.model.CreditScore;
import com.interviewtask.creditmanagerapp.model.Customer;
import com.interviewtask.creditmanagerapp.model.SimpleCustomer;
import com.interviewtask.creditmanagerapp.response.CreditResultResponse;
import com.interviewtask.creditmanagerapp.respositories.ICreditScoreRepository;
import com.interviewtask.creditmanagerapp.respositories.ICustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("creditApplication")
public class CreditApplicationController {
    private static final Integer CREDIT_MULTIPLIER = 4;

    @Autowired
    ICreditScoreRepository creditScoreRepository;

    @Autowired
    ICustomerRepository customerRepository;

    /**
     * Method for applying a credit. Credit score should be calculated beforehand to apply.
     * If an application was already made, customer cannot re-apply. The application result
     * is calculated according to monthly income and credit score.
     * @param sc Customer applying for the credit.
     * @return CreditResultResponse
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "Apply for a credit.")
    @PostMapping("")
    public ResponseEntity applyForCredit(@RequestBody SimpleCustomer sc) {
        if (!sc.validate()) {
            return ResponseEntity.status(400).body("Customer information contains empty or invalid fields");
        }
        Customer customer = new Customer(sc);
        Optional<CreditScore> byId = creditScoreRepository.findById(customer.getNationalIdNo());
        if (byId.isEmpty()) {
            return ResponseEntity.status(404).body("Credit score cannot be found for given customer");
        }
        Integer creditScore = byId.get().getCreditScore();
        Optional<Customer> customerOptional = customerRepository.findById(customer.getNationalIdNo());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.ok().body(computeCreditResult(customer, creditScore));
        } else {
            Customer customerBefore = customerOptional.get();
            return ResponseEntity.ok().body(new CreditResultResponse(
                    "An application was made for this customer before!",
                    customerBefore.getCreditResult(),
                    customerBefore.getCreditAmount()));
        }
    }

    /*
     * Computes credit result according to credit score and customer's monthly income.
     * @param customer Customer
     * @param creditScore Credit Score in Integer
     * @return CreditResultResponse
     */
    private CreditResultResponse computeCreditResult(Customer customer, Integer creditScore) {
        if (creditScore < 500) {
            updateCustomerAndSave(customer, CreditResult.REJECTED, 0);
            return new CreditResultResponse(
                    "Credit application is rejected due to low credit score!",
                    CreditResult.REJECTED,
                    0);
        } else if (creditScore < 1000) {
            if (customer.getMonthlyIncome() < 5000) {
                updateCustomerAndSave(customer, CreditResult.ACCEPTED, 10000);
                return new CreditResultResponse(
                        "Credit application is approved!",
                        CreditResult.ACCEPTED,
                        10000);
            } else {
                updateCustomerAndSave(customer, CreditResult.REJECTED, 0);
                return new CreditResultResponse(
                        "Credit application is rejected due to low credit score!",
                        CreditResult.REJECTED,
                        0);
            }
        } else {
            updateCustomerAndSave(customer, CreditResult.ACCEPTED,
                    customer.getMonthlyIncome() * CREDIT_MULTIPLIER);
            return new CreditResultResponse(
                    "Credit application is approved!",
                    CreditResult.ACCEPTED,
                    customer.getMonthlyIncome() * CREDIT_MULTIPLIER);
        }
    }

    /*
     * Update customer according to calculated credit amount and credit result, then save
     * the updated entity to db.
     * @param customer Customer
     * @param creditResult CreditResult
     * @param creditAmount Integer
     */
    private void updateCustomerAndSave(Customer customer, CreditResult creditResult, Integer creditAmount) {
        customer.setCreditResult(creditResult);
        customer.setCreditAmount(creditAmount);
        customerRepository.save(customer);
    }

}
