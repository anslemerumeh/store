package com.example.store.repository;

import com.example.store.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.orders WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :nameFragment, '%'))")
    List<Customer> findByNameContaining(String nameFragment);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.orders")
    List<Customer> findAllWithOrders();
}
