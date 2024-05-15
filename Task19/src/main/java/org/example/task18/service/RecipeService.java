package org.example.task18.service;

import lombok.AllArgsConstructor;
import org.example.task18.entity.Recipe;
import org.example.task18.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findAllRecipesByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCase(name);
    }

    public void removeRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }


}
