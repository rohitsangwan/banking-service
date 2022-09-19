package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.CardModel;
import com.bankingservice.banking.models.mysql.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpModel, Integer> {
    Optional<OtpModel> findByOtpId(Integer id);
}
