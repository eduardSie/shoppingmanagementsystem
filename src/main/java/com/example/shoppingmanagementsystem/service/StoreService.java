package com.example.shoppingmanagementsystem.service;

import com.example.shoppingmanagementsystem.model.Store;
import com.example.shoppingmanagementsystem.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    public Store save(Store store) {
        return storeRepository.save(store);
    }

    public List<Store> findStoresByProductId(Long productId) {
        return storeRepository.findByProductId(productId);
    }

    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}

