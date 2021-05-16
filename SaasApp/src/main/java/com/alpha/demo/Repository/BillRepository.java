package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.Bill;


public interface BillRepository extends JpaRepository<Bill, Long>{
	List<Bill> findByCustomerId(Long custoemrId);
	Page<Bill> findByCustomerId(Long custoemrId, Pageable pageable);
	@Query(value = "ANALYZE TABLE `bills`", nativeQuery = true) 
	String getNext();
	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE (TABLE_NAME = 'bills')", nativeQuery = true) 
	Long getNextSeriesId();
}
