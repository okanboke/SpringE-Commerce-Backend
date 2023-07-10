package com.Bitirme.Finish2.repository;

import com.Bitirme.Finish2.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
