package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardModel, Integer> {
    Optional<CardModel> findByCardNumber(Long cardNumber);
}
