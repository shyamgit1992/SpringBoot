package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Debit;

public interface DebitRepository extends JpaRepository<Debit, Long>{
	

}
