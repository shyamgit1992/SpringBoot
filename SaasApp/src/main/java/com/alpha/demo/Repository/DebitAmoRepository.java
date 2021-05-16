package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.DebitAmo;

public interface DebitAmoRepository extends JpaRepository<DebitAmo, Long> {
	List<DebitAmo> findByCustomerId(Long customerId);

}
