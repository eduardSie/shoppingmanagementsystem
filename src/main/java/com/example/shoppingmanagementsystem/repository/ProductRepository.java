package com.example.shoppingmanagementsystem.repository;

import com.example.shoppingmanagementsystem.model.Category;
import com.example.shoppingmanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();

    @Query("SELECT p FROM Product p JOIN p.stores s WHERE s.id = :storeId")
    List<Product> findByStoreId(Long storeId);
}
