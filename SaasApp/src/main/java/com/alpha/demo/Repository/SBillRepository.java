package com.alpha.demo.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.SBill;


public interface SBillRepository extends JpaRepository<SBill, Long>{
	List<SBill> findByCustomerId(Long custoemrId);
	Page<SBill> findByCustomerId(Long custoemrId, Pageable pageable);
	@Query(value = "ANALYZE TABLE `sbills`", nativeQuery = true) 
	String getNext();
	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE (TABLE_NAME = 'sbills')", nativeQuery = true) 
	Long getNextSeriesId();
}
