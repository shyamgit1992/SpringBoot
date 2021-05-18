package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.demo.model.ServiceBill;

public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long> {
	

}
