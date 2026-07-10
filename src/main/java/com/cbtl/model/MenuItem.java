package com.cbtl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "is_new", nullable = false)
    private Boolean isNew = false;

    // Nullable price fields - only the ones relevant to this item's
    // category/size-structure should be populated. The frontend renders
    // whichever of these come back non-null.
    @Column(name = "price_single")
    private Integer priceSingle;

    @Column(name = "price_double")
    private Integer priceDouble;

    @Column(name = "price_small")
    private Integer priceSmall;

    @Column(name = "price_regular")
    private Integer priceRegular;

    @Column(name = "price_large")
    private Integer priceLarge;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;
}
