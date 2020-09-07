package com.interviewtask.creditmanagerapp.respositories;

import com.interviewtask.creditmanagerapp.model.CreditScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * Credit Score Repository
 */
@Repository
public interface ICreditScoreRepository extends MongoRepository<CreditScore, String> {
}
