package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpModel, Integer> {

    OtpModel findByOtpId(Integer id);
}
