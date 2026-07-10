package com.cbtl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isNew;
    private Integer priceSingle;
    private Integer priceDouble;
    private Integer priceSmall;
    private Integer priceRegular;
    private Integer priceLarge;
    private Integer displayOrder;
}
