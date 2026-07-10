package com.cbtl.controller;

import com.cbtl.dto.MenuCategoryDto;
import com.cbtl.dto.MenuItemDto;
import com.cbtl.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // GET /api/menu/categories?group=food  or  ?group=drinks
    @GetMapping("/categories")
    public List<MenuCategoryDto> getCategories(@RequestParam String group) {
        return menuService.getCategories(group);
    }

    // GET /api/menu/items?categoryId=1
    @GetMapping("/items")
    public List<MenuItemDto> getItems(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return menuService.getItemsByCategory(categoryId);
        }
        return menuService.getAllItems();
    }

    // ---- Admin CRUD ----

    @PostMapping("/items")
    public ResponseEntity<MenuItemDto> createItem(@Valid @RequestBody MenuItemDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createItem(dto));
    }

    @PutMapping("/items/{id}")
    public MenuItemDto updateItem(@PathVariable Long id, @Valid @RequestBody MenuItemDto dto) {
        return menuService.updateItem(id, dto);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        menuService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
