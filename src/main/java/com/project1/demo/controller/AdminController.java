package com.project1.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.entity.Item;
import com.project1.demo.repository.ItemRepository;
import com.project1.demo.service.ItemService;

@RestController
public class AdminController {

    private final ItemRepository repository;
    
    private final ItemService service;

    public AdminController(ItemRepository repository, ItemService service) {
        this.repository = repository;
        this.service=service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/items")
    public List<Item> getAllItems() {
        return repository.findAll();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/items/{id}/restore")
    
    public ResponseEntity<String> restoreItem(@PathVariable Long id)
    {
    	service.restoreItem(id);
    	
    	return ResponseEntity.ok("Item restored successfully");
    }
    
}
