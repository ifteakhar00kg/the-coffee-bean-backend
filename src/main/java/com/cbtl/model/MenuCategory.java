package com.cbtl.model;

import com.cbtl.model.enums.MenuGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_group", nullable = false)
    private MenuGroup group;

    // Independent numbering per group: 1-13 for Drinks, 1-10 for Food
    @Column(name = "chapter_number", nullable = false)
    private Integer chapterNumber;

    @Column(length = 500)
    private String description;

    @Column(name = "hero_image_url", length = 1000)
    private String heroImageUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> items = new ArrayList<>();
}
