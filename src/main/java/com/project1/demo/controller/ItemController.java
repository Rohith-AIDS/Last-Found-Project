package com.project1.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.project1.demo.dto.ItemRequestDTO;
import com.project1.demo.dto.ItemResponseDTO;
import com.project1.demo.dto.ItemUpdateDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemType;
import com.project1.demo.mapper.ItemMapper;
import com.project1.demo.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;
    
    public ItemController(ItemService service) {
        this.service = service;
    }

    // CREATE (internal use / admin)
    @PostMapping
    public ItemResponseDTO createItem(@Valid @RequestBody ItemRequestDTO dto) {
        return ItemMapper.toDto(service.createItem(dto));
    }
    
    
    // FILTER BY TYPE (ACTIVE)
    @GetMapping
    public Page<ItemResponseDTO> getItems(
            @RequestParam ItemType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String dir
    ) {
        Pageable pageable = PageRequest.of(page, size,dir.equalsIgnoreCase("asc")?
        																Sort.by(sortBy).ascending():
        																	Sort.by(sortBy).descending());
        return service.getActiveItems(type, pageable)
                .map(ItemMapper::toDto);
    }

    // SEARCH BY KEYWORD
    @GetMapping("/search")
    public Page<ItemResponseDTO> searchItems(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id")String SortBy,
            @RequestParam(defaultValue = "asc")String dir
    ) {
        Pageable pageable = PageRequest.of(page, size, dir.equalsIgnoreCase("asc")?
        																Sort.by(SortBy).ascending():
        																	Sort.by(SortBy).descending());
        return service.searchItems(keyword, pageable)
                .map(ItemMapper::toDto);
    }

    // SEARCH BY LOCATION
    @GetMapping("/search/location")
    public Page<ItemResponseDTO> searchByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String dir
            
    ) {
    	Pageable pageable = PageRequest.of(page, size, dir.equalsIgnoreCase("asc")?
																		Sort.by(sortBy).ascending():
																				Sort.by(sortBy).descending());
			return service.searchActiveItemsByLocation(location, pageable)
			.map(ItemMapper::toDto);
    }

    // CLOSE ITEM
    @PatchMapping("/{id}/close")
    public ResponseEntity<String> closeItem(
            @PathVariable Long id,
            @RequestParam String user
    ) {
        service.closeItem(id, user);
        return ResponseEntity.ok("Item closed successfully");
    }
    
    @PutMapping("/{id}")
    public ItemResponseDTO updateItem(@PathVariable Long id, @RequestParam String user,@RequestBody ItemUpdateDTO dto)
    {
    	return ItemMapper.toDto(service.updatedItem(id,dto,user));
    }
    
    
    
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(
            @PathVariable Long id,
            @RequestParam String user
    ) {
        service.deleteItem(id, user);
        return ResponseEntity.ok("Item deleted successfully");
    }
}