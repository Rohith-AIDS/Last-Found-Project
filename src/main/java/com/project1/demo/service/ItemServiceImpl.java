package com.project1.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project1.demo.dto.ItemRequestDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemStatus;
import com.project1.demo.enums.ItemType;
import com.project1.demo.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	
	
	private final ItemRepository repository;
	
	 public ItemServiceImpl(ItemRepository repository) {
	        this.repository = repository;
	    }
	
	 public Item createItem(ItemRequestDTO dto) {
		    Item item = new Item();
		    item.setItemName(dto.getItemName());
		    item.setDescription(dto.getDescription());
		    item.setLocation(dto.getLocation());
		    item.setContactNumber(dto.getContactNumber());
		    item.setType(dto.getType());
		    item.setStatus(ItemStatus.ACTIVE);

		    return repository.save(item);
		}
	
	public Page<Item> getActiveItems(ItemType type, Pageable pageable)
	{
		return repository.findByTypeAndStatus(type, ItemStatus.ACTIVE, pageable);
	}
	
	@Override
	public Page<Item> searchItems(String keyword, Pageable pageable) {
	    return repository.searchActiveItems(keyword, pageable);
	}

	
	public void closeItem(Long id)
	{
		Item item=repository.findById(id).orElseThrow(()->new RuntimeException("Item not found"));
		item.setStatus(ItemStatus.CLOSED);
		repository.save(item);
	}
}
