package com.cbtl.repository;

import com.cbtl.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryIdOrderByDisplayOrderAsc(Long categoryId);
}
