package com.project1.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.project1.demo.dto.ItemRequestDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemType;
import com.project1.demo.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {
	private final ItemService service;
	
	public ItemController(ItemService service)
	{
		this.service=service;
	}
	@PostMapping
	public Item createItem(@Valid @RequestBody ItemRequestDTO dto)
	{
		return service.createItem(dto);
	}
	
	@GetMapping
	public Page<Item> getItems(@RequestParam ItemType type,@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		Pageable pageable= PageRequest.of(page, size);
		return service.getActiveItems(type,pageable);
	}
	
	@GetMapping("/search")
	public Page<Item> searchItems(
	        @RequestParam(required=false,defaultValue="") String keyword,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) {
	    Pageable pageable = PageRequest.of(page, size);
	    return service.searchItems(keyword, pageable);
	}

	@GetMapping("/items/search/location")
	public Page<Item> searchByLocation(
	        @RequestParam String location,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) {
	    return service.searchActiveItemsByLocation(location, page, size);
	}
	
	
	@PatchMapping("/items/{id}/close")
	public ResponseEntity<String> closeItem(
	        @PathVariable Long id,
	        @RequestParam String user) {
	    service.closeItem(id, user);
	    return ResponseEntity.ok("Item closed successfully");
	}

}
