package com.cbtl.repository;

import com.cbtl.model.MenuCategory;
import com.cbtl.model.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findByGroupOrderByDisplayOrderAsc(MenuGroup group);
}
