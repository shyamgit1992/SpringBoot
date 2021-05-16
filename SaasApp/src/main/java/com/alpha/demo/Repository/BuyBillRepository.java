package com.alpha.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.demo.model.BuyBill;

public interface BuyBillRepository extends JpaRepository<BuyBill, Long> {
	List<BuyBill> findByBillId(Long BillId);

}
