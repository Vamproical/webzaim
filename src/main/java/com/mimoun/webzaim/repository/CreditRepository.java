package com.mimoun.webzaim.repository;

import com.mimoun.webzaim.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface CreditRepository extends JpaRepository<Credit, UUID> {
}
