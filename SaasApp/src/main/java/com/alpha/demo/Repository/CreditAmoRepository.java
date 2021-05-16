package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.CreditAmo;

public interface CreditAmoRepository extends JpaRepository<CreditAmo, Long>{
	List<CreditAmo> findByCustomerId(Long customerId);

}
