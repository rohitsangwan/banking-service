package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardModel, Integer> {
}
