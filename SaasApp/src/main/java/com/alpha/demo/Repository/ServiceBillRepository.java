package com.alpha.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.demo.model.ServiceBill;

public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long> {
	

}
