package com.project1.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project1.demo.dto.ItemRequestDTO;
import com.project1.demo.dto.ItemUpdateDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemType;


public interface ItemService {
	Item createItem(ItemRequestDTO dto);

	Page<Item> getActiveItems(ItemType type, Pageable pageable);

    void closeItem(Long id,String user);
    
    Page<Item> searchItems(String keyword, Pageable pageable);
    
    Page<Item> searchActiveItemsByLocation(
            String location,
            Pageable pageable
    );
    void deleteItem(Long id, String user);
    
    Item updatedItem(Long id,ItemUpdateDTO dto, String user);
}
