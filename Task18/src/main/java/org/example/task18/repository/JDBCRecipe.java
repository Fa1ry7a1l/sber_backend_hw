package org.example.task18.repository;

import org.example.task18.entity.Ingredient;
import org.example.task18.entity.Recipe;
import org.example.task18.entity.RecipeIngredientLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class JDBCRecipe {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public List<Recipe> findAllRecipes() {
        return jdbcTemplate.query("Select * from recipe", new BeanPropertyRowMapper<>(Recipe.class));
    }

    @Transactional
    public List<Recipe> findRecipesByName(String name) {

        return jdbcTemplate.query("Select * from recipe r where r.name like ?",
                new String[]{"%" + name + "%"},
                new BeanPropertyRowMapper<>(Recipe.class));

    }

    @Transactional
    public void removeRecipe(Recipe recipe) {
        jdbcTemplate.update("delete from recipe r where r.id = ?", recipe.getId()
        );

    }

    @Transactional
    public Recipe addRecipe(Recipe recipe) {
        return jdbcTemplate.queryForObject("INSERT INTO recipe (name) values (?) returning *",
                new BeanPropertyRowMapper<>(Recipe.class),
                recipe.getName());
    }

    @Transactional
    public Ingredient addIngredient(Ingredient ingredient) {
        return jdbcTemplate.queryForObject("INSERT INTO ingredient (name,unit) values ( ?, ?) returning *",
                new BeanPropertyRowMapper<>(Ingredient.class),
                ingredient.getName(), ingredient.getUnit());
    }

    @Transactional
    public Ingredient findIngredient(RecipeIngredientLink link) {
        return jdbcTemplate.queryForObject("select * from ingredient i where i.id = ?",
                new BeanPropertyRowMapper<>(Ingredient.class),
                link.getIngredientId());
    }

    @Transactional
    public void addIngredientToRecipe(Recipe recipe, Ingredient ingredient, float amount) {
        jdbcTemplate.update("INSERT INTO recipe_ingredient (recipe_id,ingredient_id,amount) values (?, ?, ?)",
                recipe.getId(), ingredient.getId(), amount);
    }

    @Transactional
    public List<RecipeIngredientLink> findAllIngredientLinks(Recipe recipe) {
        return jdbcTemplate.query(" Select * from recipe_ingredient ri where ri.recipe_id = ?",
                new BeanPropertyRowMapper<>(RecipeIngredientLink.class),
                recipe.getId()
        );

    }

    @Transactional
    public List<Ingredient> findAllIngredient() {
        return jdbcTemplate.query(" Select * from ingredient",
                new BeanPropertyRowMapper<>(Ingredient.class)
        );

    }


}
