# Shopping Management System - Help Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [System Requirements](#system-requirements)
3. [Installation](#installation)
4. [Getting Started](#getting-started)
5. [Features](#features)
    - [Category Management](#category-management)
    - [Product Management](#product-management)
    - [Store Management](#store-management)
    - [Customer Management](#customer-management)
    - [Purchase Management](#purchase-management)
6. [Troubleshooting](#troubleshooting)
7. [Database Schema](#database-schema)
8. [API Reference](#api-reference)

## Introduction

The Shopping Management System is a Spring Boot application designed to help businesses manage their products, categories, stores, customers, and purchases. The application provides a user-friendly web interface built with Thymeleaf and Bootstrap, allowing you to easily track and organize your retail operations.

## System Requirements

- Java 17 or higher
- Maven 3.6 or higher
- Modern web browser (Chrome, Firefox, Safari, Edge)
- 2GB RAM (minimum)
- 500MB free disk space

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/shopping-management-system.git
   cd shopping-management-system
   ```

2. **Build the application:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   Open your web browser and navigate to `http://localhost:8080`

## Getting Started

After launching the application, you'll be greeted with the home page displaying the main menu. From here, you can access all the features of the system:

- **Manage Categories**: Create and organize product categories
- **Manage Products**: Add products and assign them to categories
- **Manage Stores**: Set up store locations and assign available products
- **Manage Customers**: Track customer information
- **Manage Purchases**: Record customer purchases

## Features

### Category Management

Categories help organize products into logical groups.

- **List Categories**: View all existing categories
- **Add New Category**: Create a new category by providing a name
- **Edit Category**: Modify an existing category's information
- **Delete Category**: Remove a category (warning: this will affect associated products)
- **View Products in Category**: See all products belonging to a specific category

### Product Management

Manage your product inventory with detailed information.

- **List Products**: View all products in the system
- **Add New Product**: Create a new product with name, price, and category
- **Edit Product**: Update an existing product's details
- **Delete Product**: Remove a product from the system
- **Sort Products**: Sort products by price (ascending or descending)
- **View Stores Selling Product**: See which stores offer a specific product
- **Add Product to Store**: Make a product available in a selected store

### Store Management

Track store locations and available products.

- **List Stores**: View all store locations
- **Add New Store**: Create a new store with name and location
- **Edit Store**: Update store information
- **Delete Store**: Remove a store from the system
- **View Store Products**: See what products are available in a specific store

### Customer Management

Keep track of your customers and their purchase history.

- **List Customers**: View all customers in the system
- **Add New Customer**: Register a new customer with name and email
- **Edit Customer**: Update customer information
- **Delete Customer**: Remove a customer from the system
- **View Customer Purchases**: See a history of purchases made by a specific customer
- **Sort Purchases**: Sort customer purchases by date or total amount

### Purchase Management

Record and track customer transactions.

- **List Purchases**: View all purchases in the system
- **Record New Purchase**: Create a new purchase record with customer, product, store, and quantity
- **Delete Purchase**: Remove a purchase record
- **Filter Purchases**: View purchases by specific criteria

## Troubleshooting

**Q: The application fails to start with database errors**  
A: Check if another application is using port 8080 or the H2 database port. You can modify the ports in `application.properties`.

**Q: Changes to data are not persisting after restart**  
A: The application uses an in-memory H2 database by default. If you need persistent storage, configure an external database in `application.properties`.

**Q: Form validation errors**  
A: Make sure all required fields are filled and follow the specified format (e.g., valid email addresses).

**Q: "Product is not available in this store" error**  
A: Before recording a purchase, make sure the product has been added to the store inventory.

## Database Schema

The system consists of the following entities and relationships:

- **Category**: Contains products
- **Product**: Belongs to a category, available in multiple stores
- **Store**: Contains multiple products
- **Customer**: Makes purchases
- **Purchase**: Links customers, products, and stores with quantity and date information

Relationships:
- One Category has many Products (one-to-many)
- One Product belongs to one Category (many-to-one)
- Products and Stores have a many-to-many relationship
- One Customer has many Purchases (one-to-many)
- One Purchase belongs to one Customer, one Product, and one Store (many-to-one)

## API Reference

The system provides REST endpoints for programmatic access:

### Category Endpoints
- GET `/categories`: List all categories
- POST `/categories`: Create a new category
- GET `/categories/edit/{id}`: Get a category for editing
- GET `/categories/delete/{id}`: Delete a category

### Product Endpoints
- GET `/products`: List all products
- GET `/products/category/{categoryId}`: List products by category
- POST `/products`: Create a new product
- GET `/products/edit/{id}`: Get a product for editing
- GET `/products/delete/{id}`: Delete a product
- GET `/products/{productId}/stores`: List stores selling a product
- POST `/products/{productId}/add-to-store`: Add a product to a store

### Store Endpoints
- GET `/stores`: List all stores
- POST `/stores`: Create a new store
- GET `/stores/edit/{id}`: Get a store for editing
- GET `/stores/delete/{id}`: Delete a store
- GET `/stores/{storeId}/products`: List products in a store

### Customer Endpoints
- GET `/customers`: List all customers
- POST `/customers`: Create a new customer
- GET `/customers/edit/{id}`: Get a customer for editing
- GET `/customers/delete/{id}`: Delete a customer
- GET `/customers/{customerId}/purchases`: View customer purchases

### Purchase Endpoints
- GET `/purchases`: List all purchases
- POST `/purchases`: Record a new purchase
- GET `/purchases/delete/{id}`: Delete a purchase
