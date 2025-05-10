package com.example.shoppingmanagementsystem.repository;

import com.example.shoppingmanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
