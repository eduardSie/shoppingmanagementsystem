package com.example.shoppingmanagementsystem.controller;

import com.example.shoppingmanagementsystem.model.Purchase;
import com.example.shoppingmanagementsystem.service.CustomerService;
import com.example.shoppingmanagementsystem.service.ProductService;
import com.example.shoppingmanagementsystem.service.PurchaseService;
import com.example.shoppingmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final StoreService storeService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService,
                              CustomerService customerService,
                              ProductService productService,
                              StoreService storeService) {
        this.purchaseService = purchaseService;
        this.customerService = customerService;
        this.productService = productService;
        this.storeService = storeService;
    }

    @GetMapping
    public String listPurchases(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchases/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("stores", storeService.findAll());
        return "purchases/form";
    }

    @PostMapping
    public String recordPurchase(@RequestParam Long customerId,
                                 @RequestParam Long productId,
                                 @RequestParam Long storeId,
                                 @RequestParam Integer quantity) {
        try {
            purchaseService.recordPurchase(customerId, productId, storeId, quantity);
            return "redirect:/purchases";
        } catch (RuntimeException e) {
            return "redirect:/purchases/new?error=" + e.getMessage();
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id) {
        purchaseService.deleteById(id);
        return "redirect:/purchases";
    }
}
