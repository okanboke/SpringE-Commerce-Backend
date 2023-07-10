package com.Bitirme.Finish2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bitirme.Finish2.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    RefreshToken findByUserId(Long userId);

}