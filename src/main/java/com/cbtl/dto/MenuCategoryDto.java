package com.cbtl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryDto {
    private Long id;
    private String name;
    private String group; // "FOOD" or "DRINKS"
    private Integer chapterNumber;
    private String description;
    private String heroImageUrl;
    private Integer displayOrder;
}
