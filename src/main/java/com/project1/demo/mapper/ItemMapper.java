package com.project1.demo.mapper;

import com.project1.demo.dto.ItemResponseDTO;
import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemStatus;

public class ItemMapper {

    private ItemMapper() {
        // prevent object creation
    }

    public static ItemResponseDTO toDto(Item item) {

        ItemResponseDTO dto = new ItemResponseDTO();

        dto.setId(item.getId());
        dto.setItemName(item.getItemName());
        dto.setDescription(item.getDescription());
        dto.setLocation(item.getLocation());
        dto.setType(item.getType());
        dto.setStatus(item.getStatus());

        // 🔐 Conditional exposure
        if (item.getStatus() == ItemStatus.ACTIVE) {
            dto.setContactNumber(item.getContactNumber());
        } else {
            dto.setContactNumber(null);
        }

        return dto;
    }
}