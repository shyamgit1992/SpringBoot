package com.alpha.demo.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.Dealers;

public interface DealerRepository extends JpaRepository<Dealers, Long>{
	

}
