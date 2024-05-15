package org.example.task18.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientLink {
    private Integer recipeId;
    private Integer ingredientId;

    private Float amount;
}
