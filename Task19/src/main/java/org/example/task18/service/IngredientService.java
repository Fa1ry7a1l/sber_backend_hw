package org.example.task18.service;

import lombok.AllArgsConstructor;
import org.example.task18.entity.Ingredient;
import org.example.task18.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;


    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }
}
