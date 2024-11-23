package com.harsh.crm.repository.jpa;

import com.harsh.crm.dto.Customer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface User_Details_Repo extends JpaRepository<Customer,Integer> {
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE customer_data AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
