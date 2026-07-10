package com.cbtl.service;

import com.cbtl.dto.MenuCategoryDto;
import com.cbtl.dto.MenuItemDto;
import com.cbtl.model.MenuCategory;
import com.cbtl.model.MenuGroup;
import com.cbtl.model.MenuItem;
import com.cbtl.repository.MenuCategoryRepository;
import com.cbtl.repository.MenuItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository itemRepository;

    public MenuService(MenuCategoryRepository categoryRepository, MenuItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public List<MenuCategoryDto> getCategories(String group) {
        MenuGroup menuGroup = MenuGroup.valueOf(group.toUpperCase());
        return categoryRepository.findByGroupOrderByDisplayOrderAsc(menuGroup)
                .stream().map(this::toCategoryDto).toList();
    }

    public List<MenuItemDto> getItemsByCategory(Long categoryId) {
        return itemRepository.findByCategoryIdOrderByDisplayOrderAsc(categoryId)
                .stream().map(this::toItemDto).toList();
    }

    public List<MenuItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(this::toItemDto).toList();
    }

    public MenuItemDto createItem(MenuItemDto dto) {
        MenuCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));

        MenuItem item = new MenuItem();
        applyDtoToEntity(dto, item, category);
        MenuItem saved = itemRepository.save(item);
        return toItemDto(saved);
    }

    public MenuItemDto updateItem(Long id, MenuItemDto dto) {
        MenuItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
        MenuCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));

        applyDtoToEntity(dto, item, category);
        MenuItem saved = itemRepository.save(item);
        return toItemDto(saved);
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item not found: " + id);
        }
        itemRepository.deleteById(id);
    }

    private void applyDtoToEntity(MenuItemDto dto, MenuItem item, MenuCategory category) {
        item.setCategory(category);
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setImageUrl(dto.getImageUrl());
        item.setIsNew(Boolean.TRUE.equals(dto.getIsNew()));
        item.setPriceSingle(dto.getPriceSingle());
        item.setPriceDouble(dto.getPriceDouble());
        item.setPriceSmall(dto.getPriceSmall());
        item.setPriceRegular(dto.getPriceRegular());
        item.setPriceLarge(dto.getPriceLarge());
        item.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0);
    }

    private MenuCategoryDto toCategoryDto(MenuCategory c) {
        return new MenuCategoryDto(
                c.getId(), c.getName(), c.getGroup().name(), c.getChapterNumber(),
                c.getDescription(), c.getHeroImageUrl(), c.getDisplayOrder()
        );
    }

    private MenuItemDto toItemDto(MenuItem i) {
        return new MenuItemDto(
                i.getId(), i.getCategory().getId(), i.getName(), i.getDescription(), i.getImageUrl(),
                i.getIsNew(), i.getPriceSingle(), i.getPriceDouble(), i.getPriceSmall(),
                i.getPriceRegular(), i.getPriceLarge(), i.getDisplayOrder()
        );
    }
}
