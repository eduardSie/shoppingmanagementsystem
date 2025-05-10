package com.example.shoppingmanagementsystem.repository;

import com.example.shoppingmanagementsystem.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s JOIN s.products p WHERE p.id = :productId")
    List<Store> findByProductId(Long productId);
}
