package com.cacutler.recipearchive.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private Long userId;//May need to add a private string username attribute
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private String allergies;
    private Integer prepTime;
    private Integer cookingTime;
    private Integer servings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}