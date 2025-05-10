package com.example.shoppingmanagementsystem.controller;

import com.example.shoppingmanagementsystem.model.Store;
import com.example.shoppingmanagementsystem.service.ProductService;
import com.example.shoppingmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final ProductService productService;

    @Autowired
    public StoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping
    public String listStores(Model model) {
        model.addAttribute("stores", storeService.findAll());
        return "stores/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("store", new Store());
        return "stores/form";
    }

    @PostMapping
    public String createStore(@Valid @ModelAttribute("store") Store store,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "stores/form";
        }
        storeService.save(store);
        return "redirect:/stores";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Store store = storeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid store Id:" + id));
        model.addAttribute("store", store);
        return "stores/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteById(id);
        return "redirect:/stores";
    }

    @GetMapping("/{storeId}/products")
    public String viewStoreProducts(@PathVariable Long storeId, Model model) {
        model.addAttribute("store", storeService.findById(storeId).orElseThrow());
        model.addAttribute("products", productService.findByStoreId(storeId));
        return "stores/products";
    }
}
