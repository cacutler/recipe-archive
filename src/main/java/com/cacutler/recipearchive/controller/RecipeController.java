package com.cacutler.recipearchive.controller;

import com.cacutler.recipearchive.dto.RecipeCreateDTO;
import com.cacutler.recipearchive.dto.RecipeDTO;
import com.cacutler.recipearchive.dto.RecipeUpdateDTO;
import com.cacutler.recipearchive.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecipeDTO>> getRecipesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recipeService.getRecipesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeCreateDTO recipeCreateDTO) {
        RecipeDTO created = recipeService.createRecipe(recipeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(
            @PathVariable Long id,
            @Valid @RequestBody RecipeUpdateDTO recipeUpdateDTO) {
        RecipeDTO updated = recipeService.updateRecipe(id, recipeUpdateDTO);
        return ResponseEntity.ok(updated);
    }
}