package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.demo.model.ServiceSell;


public interface ServiceSellRepository extends JpaRepository<ServiceSell, Long>{
	@Query(value = "ANALYZE TABLE `service_sell`", nativeQuery = true) 
	String getNext();
	@Query(value = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE (TABLE_NAME = 'service_sell')", nativeQuery = true) 
	Long getNextSeriesId();
}
