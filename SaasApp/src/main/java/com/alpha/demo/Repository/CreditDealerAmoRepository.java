package com.alpha.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.CreditDealerAmo;

public interface CreditDealerAmoRepository extends JpaRepository<CreditDealerAmo, Long>{
	List<CreditDealerAmo> findByDealersId(Long dealersId);

}
