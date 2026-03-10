package com.project1.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.entity.Item;
import com.project1.demo.repository.ItemRepository;

@RestController
public class AdminController {

    private final ItemRepository repository;

    public AdminController(ItemRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/items")
    public List<Item> getAllItems() {
        return repository.findAll();
    }
}
