package com.example.shoppingmanagementsystem.controller;

import com.example.shoppingmanagementsystem.model.Customer;
import com.example.shoppingmanagementsystem.service.CustomerService;
import com.example.shoppingmanagementsystem.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final PurchaseService purchaseService;

    @Autowired
    public CustomerController(CustomerService customerService, PurchaseService purchaseService) {
        this.customerService = customerService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customers/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/purchases")
    public String viewCustomerPurchases(@PathVariable Long customerId,
                                        @RequestParam(required = false) String sort,
                                        Model model) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerId));

        if ("dateAsc".equals(sort)) {
            model.addAttribute("purchases", purchaseService.findPurchasesByCustomerIdSortedByDate(customerId, true));
        } else if ("dateDesc".equals(sort)) {
            model.addAttribute("purchases", purchaseService.findPurchasesByCustomerIdSortedByDate(customerId, false));
        } else if ("totalAsc".equals(sort)) {
            model.addAttribute("purchases", purchaseService.findPurchasesByCustomerIdSortedByTotal(customerId, true));
        } else if ("totalDesc".equals(sort)) {
            model.addAttribute("purchases", purchaseService.findPurchasesByCustomerIdSortedByTotal(customerId, false));
        } else {
            model.addAttribute("purchases", purchaseService.findPurchasesByCustomerId(customerId));
        }

        model.addAttribute("customer", customer);
        return "customers/purchases";
    }
}
