package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long>{
	

}
