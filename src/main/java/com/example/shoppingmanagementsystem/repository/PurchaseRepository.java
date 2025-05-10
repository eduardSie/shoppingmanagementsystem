package com.example.shoppingmanagementsystem.repository;

import com.example.shoppingmanagementsystem.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerId(Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.customer.id = :customerId ORDER BY p.purchaseDate ASC")
    List<Purchase> findByCustomerIdOrderByDateAsc(Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.customer.id = :customerId ORDER BY p.purchaseDate DESC")
    List<Purchase> findByCustomerIdOrderByDateDesc(Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.customer.id = :customerId ORDER BY p.product.price * p.quantity ASC")
    List<Purchase> findByCustomerIdOrderByTotalAsc(Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.customer.id = :customerId ORDER BY p.product.price * p.quantity DESC")
    List<Purchase> findByCustomerIdOrderByTotalDesc(Long customerId);
}
