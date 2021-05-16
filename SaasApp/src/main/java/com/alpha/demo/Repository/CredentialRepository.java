package com.alpha.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.demo.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

}
