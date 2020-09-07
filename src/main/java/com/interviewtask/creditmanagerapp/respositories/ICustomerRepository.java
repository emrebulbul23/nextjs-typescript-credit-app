package com.interviewtask.creditmanagerapp.respositories;

import com.interviewtask.creditmanagerapp.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * Customer Repository
 */
@Repository
public interface ICustomerRepository extends MongoRepository<Customer, String> {
}
