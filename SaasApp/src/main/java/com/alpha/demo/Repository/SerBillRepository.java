package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.SerBill;


public interface SerBillRepository extends JpaRepository<SerBill, Long>{
	List<SerBill> findByCustomerId(Long customerId);
	//List<SerBill> findAllByOrderByBillingDateAsc();
	@Query(value = "ANALYZE TABLE `serbills`", nativeQuery = true) 
	String getNext();
	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE (TABLE_NAME = 'serbills')", nativeQuery = true) 
	Long getNextSeriesId();
	@Query(value ="SELECT * FROM serbills ORDER BY billing_date DESC", nativeQuery = true)
	List<SerBill> findAllOrderByDateAsc();
}
