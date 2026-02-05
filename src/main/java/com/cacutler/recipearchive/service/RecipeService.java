package com.cacutler.recipearchive.service;

import com.cacutler.recipearchive.dto.RecipeCreateDTO;
import com.cacutler.recipearchive.dto.RecipeDTO;
import com.cacutler.recipearchive.dto.RecipeUpdateDTO;
import com.cacutler.recipearchive.entity.Recipe;
import com.cacutler.recipearchive.entity.User;
import com.cacutler.recipearchive.exception.ResourceNotFoundException;
import com.cacutler.recipearchive.repository.RecipeRepository;
import com.cacutler.recipearchive.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        return convertToDTO(recipe);
    }

    public List<RecipeDTO> getRecipesByUserId(Long userId) {
        return recipeRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RecipeDTO createRecipe(RecipeCreateDTO recipeCreateDTO) {
        User user = userRepository.findById(recipeCreateDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + recipeCreateDTO.getUserId()));
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setTitle(recipeCreateDTO.getTitle());
        recipe.setDescription(recipeCreateDTO.getDescription());
        recipe.setIngredients(recipeCreateDTO.getIngredients());
        recipe.setInstructions(recipeCreateDTO.getInstructions());
        recipe.setAllergies(recipeCreateDTO.getAllergies());
        recipe.setPrepTime(recipeCreateDTO.getPrepTime());
        recipe.setCookingTime(recipeCreateDTO.getCookingTime());
        recipe.setServings(recipeCreateDTO.getServings());
        Recipe savedRecipe = recipeRepository.save(recipe);
        return convertToDTO(savedRecipe);
    }

    public RecipeDTO updateRecipe(Long id, RecipeUpdateDTO recipeUpdateDTO) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        if (recipeUpdateDTO.getTitle() != null) {
            recipe.setTitle(recipeUpdateDTO.getTitle());
        }
        if (recipeUpdateDTO.getDescription() != null) {
            recipe.setDescription(recipeUpdateDTO.getDescription());
        }
        if (recipeUpdateDTO.getIngredients() != null) {
            recipe.setIngredients(recipeUpdateDTO.getIngredients());
        }
        if (recipeUpdateDTO.getInstructions() != null) {
            recipe.setInstructions(recipeUpdateDTO.getInstructions());
        }
        if (recipeUpdateDTO.getAllergies() != null) {
            recipe.setAllergies(recipeUpdateDTO.getAllergies());
        }
        if (recipeUpdateDTO.getPrepTime() != null) {
            recipe.setPrepTime(recipeUpdateDTO.getPrepTime());
        }
        if (recipeUpdateDTO.getCookingTime() != null) {
            recipe.setCookingTime(recipeUpdateDTO.getCookingTime());
        }
        if (recipeUpdateDTO.getServings() != null) {
            recipe.setServings(recipeUpdateDTO.getServings());
        }
        Recipe updatedRecipe = recipeRepository.save(recipe);
        return convertToDTO(updatedRecipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
        recipeRepository.delete(recipe);
    }

    private RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO dto = modelMapper.map(recipe, RecipeDTO.class);
        dto.setUserId(recipe.getUser().getId());
        return dto;
    }
}