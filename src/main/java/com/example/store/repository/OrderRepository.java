package com.example.store.repository;

import com.example.store.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.customer LEFT JOIN FETCH o.products")
    List<Order> findAllWithDetails();

    @Query("SELECT o FROM Order o JOIN FETCH o.customer LEFT JOIN FETCH o.products WHERE o.id = :id")
    Optional<Order> findByIdWithDetails(Long id);
}
