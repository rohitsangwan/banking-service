package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.RegisterUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The register user repository
 */
@Repository
public interface RegisterUserRepository extends JpaRepository<RegisterUserModel, Integer>{
    Optional<RegisterUserModel> findByUserId(String userId);
}