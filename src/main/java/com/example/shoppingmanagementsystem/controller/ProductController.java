package com.example.shoppingmanagementsystem.controller;

import com.example.shoppingmanagementsystem.model.Product;
import com.example.shoppingmanagementsystem.service.CategoryService;
import com.example.shoppingmanagementsystem.service.ProductService;
import com.example.shoppingmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final StoreService storeService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, StoreService storeService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.storeService = storeService;
    }

    @GetMapping
    public String listProducts(@RequestParam(required = false) String sort, Model model) {
        if ("priceAsc".equals(sort)) {
            model.addAttribute("products", productService.findAllSortedByPriceAsc());
        } else if ("priceDesc".equals(sort)) {
            model.addAttribute("products", productService.findAllSortedByPriceDesc());
        } else {
            model.addAttribute("products", productService.findAll());
        }
        return "products/list";
    }

    @GetMapping("/category/{categoryId}")
    public String listProductsByCategory(@PathVariable Long categoryId, Model model) {
        model.addAttribute("products", productService.findByCategory(categoryId));
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryService.findById(categoryId).get().getName());
        return "products/by-category";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult result,
                                @RequestParam Long categoryId,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "products/form";
        }
        productService.addProductToCategory(product, categoryId);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "products/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/{productId}/stores")
    public String listStoresSellingProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findById(productId).orElseThrow());
        model.addAttribute("stores", storeService.findStoresByProductId(productId));
        return "products/stores-list";
    }

    @GetMapping("/{productId}/add-to-store")
    public String showAddToStoreForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findById(productId).orElseThrow());
        model.addAttribute("stores", storeService.findAll());
        return "products/add-to-store";
    }

    @PostMapping("/{productId}/add-to-store")
    public String addProductToStore(@PathVariable Long productId, @RequestParam Long storeId) {
        productService.addProductToStore(productId, storeId);
        return "redirect:/products/" + productId + "/stores";
    }
}
