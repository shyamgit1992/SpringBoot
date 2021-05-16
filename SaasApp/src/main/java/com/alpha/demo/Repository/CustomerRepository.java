package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
