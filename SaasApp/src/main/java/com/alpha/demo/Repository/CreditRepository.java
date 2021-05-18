package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long>{
	

}
