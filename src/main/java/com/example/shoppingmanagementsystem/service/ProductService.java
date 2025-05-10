package com.example.shoppingmanagementsystem.service;

import com.example.shoppingmanagementsystem.model.Category;
import com.example.shoppingmanagementsystem.model.Product;
import com.example.shoppingmanagementsystem.model.Store;
import com.example.shoppingmanagementsystem.repository.CategoryRepository;
import com.example.shoppingmanagementsystem.repository.ProductRepository;
import com.example.shoppingmanagementsystem.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product addProductToCategory(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product addProductToStore(Long productId, Long storeId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        if (!store.getProducts().contains(product)) {
            store.getProducts().add(product);
            storeRepository.save(store);
        }

        return product;
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public boolean isProductInStore(Long productId, Long storeId) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (storeOpt.isPresent() && productOpt.isPresent()) {
            Store store = storeOpt.get();
            Product product = productOpt.get();
            return store.getProducts().contains(product);
        }
        return false;
    }

    public List<Product> findAllSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> findAllSortedByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    public List<Product> findByStoreId(Long storeId) {
        return productRepository.findByStoreId(storeId);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

