package com.cacutler.recipearchive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotBlank(message = "Ingredients are required")
    private String ingredients;
    @NotBlank(message = "Instructions are required")
    private String instructions;
    private String allergies;
    @Positive(message = "Prep time must be positive")
    private Integer prepTime;
    @Positive(message = "Cooking time must be positive")
    private Integer cookingTime;
    @Positive(message = "Servings must be positive")
    private Integer servings;
}