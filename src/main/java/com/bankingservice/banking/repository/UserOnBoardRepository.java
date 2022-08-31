package com.bankingservice.banking.repository;

import com.bankingservice.banking.models.mysql.UserOnBoardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user on board repository
 */
@Repository
public interface UserOnBoardRepository extends JpaRepository<UserOnBoardModel, Integer> {
    Optional<UserOnBoardModel> findByRegisterUserId(Integer id);
}
