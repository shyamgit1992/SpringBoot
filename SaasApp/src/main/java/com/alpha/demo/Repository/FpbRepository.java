package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.FirmPurchaseBill;

public interface FpbRepository extends JpaRepository<FirmPurchaseBill, Long>{
	List<FirmPurchaseBill> findByDealersId(Long dealersId);
	Page<FirmPurchaseBill> findByDealersId(Long dealersId, Pageable pageable);
	@Query(value = "ANALYZE TABLE `firm_purchase_bills`", nativeQuery = true) 
	String getNext();
	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE (TABLE_NAME = 'firm_purchase_bills')", nativeQuery = true) 
	Long getNextSeriesId();

}
