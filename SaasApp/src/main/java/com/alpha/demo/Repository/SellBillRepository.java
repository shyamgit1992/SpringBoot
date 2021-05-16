package com.alpha.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.demo.model.SellBill;

public interface SellBillRepository extends JpaRepository<SellBill, Long> {
	List<SellBill> findBySbillId(Long BillId);

}
