package com.project1.demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project1.demo.entity.Item;
import com.project1.demo.enums.ItemStatus;
import com.project1.demo.enums.ItemType;

public interface ItemRepository extends JpaRepository<Item,Long> {
	
	Page<Item> findByTypeAndStatusAndDeletedFalse(ItemType type,ItemStatus status,Pageable pageable);
	@Query("""
			   SELECT i FROM Item i
			   WHERE i.status = 'ACTIVE'
			   AND i.deleted = false
			   AND (LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			   OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
			""")
			Page<Item> searchActiveItems(
			    @Param("keyword") String keyword,
			    Pageable pageable
			);
	
	@Query("""
		       SELECT i FROM Item i
		       WHERE i.status = 'ACTIVE'
		       AND i.deleted = false
		       AND LOWER(i.location) LIKE LOWER(CONCAT('%', :location, '%'))
		       """)
		Page<Item> searchActiveItemsByLocation(
		        @Param("location") String location,
		        Pageable pageable
		);
	
}


