package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Dealers;

public interface DealerRepository extends JpaRepository<Dealers, Long>{
	

}
