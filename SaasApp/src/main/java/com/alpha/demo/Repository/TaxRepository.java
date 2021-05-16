package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Tax;


public interface TaxRepository extends JpaRepository<Tax, Long>{
	
}
