package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Dealers;
import com.alpha.demo.model.FirmSellBillItem;

public interface FsbiRepository  extends JpaRepository<FirmSellBillItem, Long>{
	List<FirmSellBillItem> findByFirmSellBillId(Long FirmSellBillId);
	List<FirmSellBillItem> findByName(String Name);
	//List<FirmPurchaseBillItem> findByNameAndDealersId(String Name,Long DealersId);
	
	
}
