package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.RegisterUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The register user repository
 */
@Repository
public interface RegisterUserRepository extends JpaRepository<RegisterUserModel, Integer>{
}