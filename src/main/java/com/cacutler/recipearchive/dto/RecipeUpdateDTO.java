package com.cacutler.recipearchive.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUpdateDTO {
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private String allergies;
    @Positive(message = "Prep time must be positive")
    private Integer prepTime;
    @Positive(message = "Cooking time must be positive")
    private Integer cookingTime;
    @Positive(message = "Servings must be positive")
    private Integer servings;
}