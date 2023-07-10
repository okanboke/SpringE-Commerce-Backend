package com.Bitirme.Finish2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bitirme.Finish2.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
