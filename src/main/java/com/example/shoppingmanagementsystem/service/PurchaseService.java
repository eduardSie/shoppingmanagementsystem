package com.example.shoppingmanagementsystem.service;

import com.example.shoppingmanagementsystem.model.Customer;
import com.example.shoppingmanagementsystem.model.Product;
import com.example.shoppingmanagementsystem.model.Purchase;
import com.example.shoppingmanagementsystem.model.Store;
import com.example.shoppingmanagementsystem.repository.CustomerRepository;
import com.example.shoppingmanagementsystem.repository.ProductRepository;
import com.example.shoppingmanagementsystem.repository.PurchaseRepository;
import com.example.shoppingmanagementsystem.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ProductService productService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           StoreRepository storeRepository,
                           ProductService productService) {
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.productService = productService;
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase recordPurchase(Long customerId, Long productId, Long storeId, Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // Check if product is available in the store
        if (!productService.isProductInStore(productId, storeId)) {
            throw new RuntimeException("Product is not available in this store");
        }

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setProduct(product);
        purchase.setStore(store);
        purchase.setQuantity(quantity);
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> findPurchasesByCustomerId(Long customerId) {
        return purchaseRepository.findByCustomerId(customerId);
    }

    public List<Purchase> findPurchasesByCustomerIdSortedByDate(Long customerId, boolean ascending) {
        if (ascending) {
            return purchaseRepository.findByCustomerIdOrderByDateAsc(customerId);
        } else {
            return purchaseRepository.findByCustomerIdOrderByDateDesc(customerId);
        }
    }

    public List<Purchase> findPurchasesByCustomerIdSortedByTotal(Long customerId, boolean ascending) {
        if (ascending) {
            return purchaseRepository.findByCustomerIdOrderByTotalAsc(customerId);
        } else {
            return purchaseRepository.findByCustomerIdOrderByTotalDesc(customerId);
        }
    }

    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }
}
