package com.springboot.microservice.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends
        CrudRepository<Customer, Long> {
}