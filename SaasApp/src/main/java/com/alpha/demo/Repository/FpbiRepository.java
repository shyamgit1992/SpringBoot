package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.FirmPurchaseBillItem;

public interface FpbiRepository  extends JpaRepository<FirmPurchaseBillItem, Long>{
	String stock = null;
	List<FirmPurchaseBillItem> findByFirmPurchaseBillId(Long FirmPurchaseBillId);
	List<FirmPurchaseBillItem> findByName(String Name);
	//List<FirmPurchaseBillItem> findByNameAndDealersId(String Name,Long DealersId);
	
	
}
