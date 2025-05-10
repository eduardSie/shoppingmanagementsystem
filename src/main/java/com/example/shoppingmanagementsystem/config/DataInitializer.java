package com.example.shoppingmanagementsystem.config;

import com.example.shoppingmanagementsystem.model.*;
import com.example.shoppingmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public DataInitializer(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           StoreRepository storeRepository,
                           CustomerRepository customerRepository,
                           PurchaseRepository purchaseRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(String... args) {
        // Create categories
        Category electronics = new Category();
        electronics.setName("Electronics");

        Category groceries = new Category();
        groceries.setName("Groceries");

        Category clothing = new Category();
        clothing.setName("Clothing");

        categoryRepository.saveAll(Arrays.asList(electronics, groceries, clothing));

        // Create products
        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setPrice(999.99);
        laptop.setCategory(electronics);

        Product smartphone = new Product();
        smartphone.setName("Smartphone");
        smartphone.setPrice(499.99);
        smartphone.setCategory(electronics);

        Product milk = new Product();
        milk.setName("Milk");
        milk.setPrice(1.99);
        milk.setCategory(groceries);

        Product bread = new Product();
        bread.setName("Bread");
        bread.setPrice(2.49);
        bread.setCategory(groceries);

        Product tshirt = new Product();
        tshirt.setName("T-Shirt");
        tshirt.setPrice(19.99);
        tshirt.setCategory(clothing);

        productRepository.saveAll(Arrays.asList(laptop, smartphone, milk, bread, tshirt));

        // Create stores
        Store techStore = new Store();
        techStore.setName("TechMaster");
        techStore.setLocation("Downtown");

        Store supermarket = new Store();
        supermarket.setName("SuperMart");
        supermarket.setLocation("Uptown");

        Store fashionStore = new Store();
        fashionStore.setName("FashionHub");
        fashionStore.setLocation("Mall");

        storeRepository.saveAll(Arrays.asList(techStore, supermarket, fashionStore));

        // Add products to stores
        techStore.getProducts().addAll(Arrays.asList(laptop, smartphone));
        supermarket.getProducts().addAll(Arrays.asList(milk, bread));
        fashionStore.getProducts().add(tshirt);
        supermarket.getProducts().add(tshirt); // Some clothes in supermarket too

        storeRepository.saveAll(Arrays.asList(techStore, supermarket, fashionStore));

        // Create customers
        Customer john = new Customer();
        john.setName("John Doe");
        john.setEmail("john@example.com");

        Customer jane = new Customer();
        jane.setName("Jane Smith");
        jane.setEmail("jane@example.com");

        customerRepository.saveAll(Arrays.asList(john, jane));

        // Create purchases
        Purchase purchase1 = new Purchase();
        purchase1.setCustomer(john);
        purchase1.setProduct(laptop);
        purchase1.setStore(techStore);
        purchase1.setQuantity(1);
        purchase1.setPurchaseDate(LocalDateTime.now().minusDays(5));

        Purchase purchase2 = new Purchase();
        purchase2.setCustomer(john);
        purchase2.setProduct(milk);
        purchase2.setStore(supermarket);
        purchase2.setQuantity(2);
        purchase2.setPurchaseDate(LocalDateTime.now().minusDays(2));

        Purchase purchase3 = new Purchase();
        purchase3.setCustomer(jane);
        purchase3.setProduct(tshirt);
        purchase3.setStore(fashionStore);
        purchase3.setQuantity(3);
        purchase3.setPurchaseDate(LocalDateTime.now().minusDays(1));

        purchaseRepository.saveAll(Arrays.asList(purchase1, purchase2, purchase3));
    }
}