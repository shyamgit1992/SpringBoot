package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Check;

public interface CheckRepository extends JpaRepository<Check, Long> {

}
