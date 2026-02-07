package com.project1.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project1.demo.dto.ItemRequestDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemStatus;
import com.project1.demo.enums.ItemType;
import com.project1.demo.exception.BadRequestException;
import com.project1.demo.exception.ForbiddenException;
import com.project1.demo.exception.ResourceNotFoundException;
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
		    item.setCreatedBy(dto.getCreatedBy());

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
	
	public Page<Item> searchActiveItemsByLocation(String location,int page,int size)
	{
		return repository.searchActiveItemsByLocation(location,
				PageRequest.of(page, size));
	}
	
	public void closeItem(Long id, String user) {
	    Item item = repository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

	    if (!item.getStatus().equals(ItemStatus.ACTIVE)) {
	        throw new BadRequestException("Item already closed");
	    }

	    if (!item.getCreatedBy().equals(user)) {
	        throw new ForbiddenException("You are not allowed to close this item");
	    }

	    item.setStatus(ItemStatus.CLOSED);
	    repository.save(item);
	}

	public Item updatedItem(Long id,Item updatedItem)
	{
		Item item=repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Item not found"));
	
	
	if(item.getStatus()!=ItemStatus.ACTIVE)
	{
		throw new BadRequestException("Closed items cannot be updated");
	}
	
	item.setItemName(updatedItem.getItemName());
    item.setDescription(updatedItem.getDescription());
    item.setLocation(updatedItem.getLocation());
    item.setContactNumber(updatedItem.getContactNumber());

    return repository.save(item);
	}
}
