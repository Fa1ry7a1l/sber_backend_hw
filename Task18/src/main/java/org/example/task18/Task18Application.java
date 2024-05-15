package org.example.task18;

import lombok.extern.java.Log;
import org.example.task18.entity.Ingredient;
import org.example.task18.entity.Recipe;
import org.example.task18.repository.JDBCRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Scanner;

@SpringBootApplication
public class Task18Application implements ApplicationRunner {

    private Scanner scanner = new Scanner(System.in);


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JDBCRecipe jdbcRecipe;

    public static void main(String[] args) {
        SpringApplication.run(Task18Application.class, args);

    }

    @Override
    public void run(ApplicationArguments args) {

        //это должно быть в liquidbase, но для примера и так пойдет
        jdbcTemplate.execute("""
                Create table if not exists ingredient(
                	id       BIGINT GENERATED ALWAYS AS IDENTITY,
                	name     VARCHAR(256) NOT NULL,
                	unit	 varchar(256) NOT null,
                	PRIMARY KEY (id)
                );

                create table if not exists recipe(
                	id    BIGINT GENERATED ALWAYS AS IDENTITY,
                    name VARCHAR(256) NOT NULL,
                    PRIMARY KEY (id)
                );

                CREATE TABLE IF NOT EXISTS recipe_ingredient
                (
                    recipe_id BIGINT REFERENCES recipe (id) ON DELETE CASCADE,
                    ingredient_id BIGINT REFERENCES ingredient (id),
                	amount float,
                    PRIMARY KEY (recipe_id, ingredient_id)
                )
                                """);


        while (true) {
            System.out.println("""
                    Введите номер команды
                    1. Вывести весь список рецептов
                    2. показать рецепт
                    3. Добавить рецепт
                    4. Удалить рецепт
                    5. Поиск рецепта по названию
                    0. Выход
                    """);

            int command = scanner.nextInt();
            reactCommand(command);


        }
    }

    private void reactCommand(int command) {
        switch (command) {
            case 1:
                showAllRecipes();
                break;
            case 3:
                addRecipe();
                break;
            case 2:
                showRecipe();
                break;
            case 4:
                removeRecipe();
                break;
            case 5:
                findRecipeByName();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    private void findRecipeByName() {
        System.out.println("Введите название рецепта, или его часть");
        String name = scanner.nextLine().trim().toUpperCase();
        if (name.isEmpty()) {
            name = scanner.nextLine().trim().toUpperCase();
        }

        var searchResult = jdbcRecipe.findRecipesByName(name);
        if (searchResult.isEmpty()) {
            System.out.println("Ничего не нашлось");
            return;
        }

        System.out.println("Вот что мы нашли");
        for (var recipe : searchResult) {
            System.out.printf("%d. %s\n", recipe.getId(), recipe.getName());
        }
        System.out.println("Это все");
    }

    private void removeRecipe() {
        System.out.println("Введите номер рецепта, который хотите удалить");
        var allRecipes = jdbcRecipe.findAllRecipes();
        if (allRecipes.isEmpty()) {
            System.out.println("А у вас нет рецептов");
            return;
        }

        for (var recipe : allRecipes) {
            System.out.printf("%d. %s\n", recipe.getId(), recipe.getName());
        }
        int answer = scanner.nextInt();
        var selectedRecipe = allRecipes.stream().filter(recipe -> recipe.getId().equals(answer)).findFirst();

        if (selectedRecipe.isEmpty()) {
            System.out.println("Не знаю такого рецепта");
        } else {
            jdbcRecipe.removeRecipe(selectedRecipe.get());
            System.out.println("Удален");
        }
    }

    private void showRecipe() {
        var allRecipes = jdbcRecipe.findAllRecipes();

        System.out.println("Введите номер рецепта, который хотите посмотреть");

        for (var recipe : allRecipes) {
            System.out.printf("%d. %s\n", recipe.getId(), recipe.getName());
        }
        int answer = scanner.nextInt();

        if (allRecipes.stream().noneMatch(recipe -> recipe.getId().equals(answer))) {
            System.out.println("Не нашлось совпадений");
            return;
        }

        var recipe = allRecipes.stream().filter(r -> r.getId().equals(answer)).findFirst().get();
        System.out.println("Рецепт " + recipe.getName());

        var ingredientList = jdbcRecipe.findAllIngredientLinks(recipe);

        for (int i = 0; i < ingredientList.size(); i++) {
            var ingredient = jdbcRecipe.findIngredient(ingredientList.get(i));
            System.out.printf("%d. %s %.1f %s\n", i + 1, ingredient.getName(), ingredientList.get(i).getAmount(), ingredient.getUnit());
        }

        System.out.println("Вот и весь рецепт");
    }

    private void addRecipe() {
        boolean notCorrectRecipeName = true;
        String recipeName = "";
        while (notCorrectRecipeName) {
            System.out.println("Введите название рецепта");
            recipeName = scanner.next().trim().toUpperCase();
            var findedRecipes = jdbcRecipe.findRecipesByName(recipeName);
            String finalRecipeName = recipeName;
            if (findedRecipes.stream().noneMatch(recipe -> recipe.getName().equals(finalRecipeName))) {
                notCorrectRecipeName = false;
            } else {
                System.out.println("Рецепт с таким именем уже существует");
            }
        }
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe = jdbcRecipe.addRecipe(recipe);

        System.out.println("Рецепт успешно добавлен");

        while (true) {
            System.out.println("""
                    Добавить ингредиент?
                    1. Да
                    0. Закончить
                    """);

            int answer = scanner.nextInt();

            if (answer == 0) {
                break;
            }

            var ingredient = addIngredient();
            if (ingredient != null) {
                addIngredientToRecipe(recipe, ingredient);
            }


        }
    }

    private void addIngredientToRecipe(Recipe recipe, Ingredient ingredient) {

        float amount = -10;

        while (amount <= 0) {
            System.out.println("Введите сколько ингридиента нужно в " + ingredient.getUnit());
            amount = scanner.nextFloat();

            if (amount <= 0) {
                System.out.println("Не может быть столько ингредиента");
            }
        }

        jdbcRecipe.addIngredientToRecipe(recipe, ingredient, amount);
    }

    private Ingredient addIngredient() {
        int answer;
        var allIngredient = jdbcRecipe.findAllIngredient();
        System.out.println("0. Добавить новый ингредиент");
        for (var i : allIngredient) {
            System.out.printf("%d. %s %s\n", i.getId(), i.getName(), i.getUnit());
        }

        answer = scanner.nextInt();
        if (answer != 0) {
            var resultIngredient = allIngredient.stream().filter(ingredient -> ingredient.getId().equals(answer)).findFirst();
            if (resultIngredient.isPresent()) {
                return resultIngredient.get();
            } else {
                System.out.println("Введен неверный номер ингредиента");
            }
        } else {
            String ingredientName = "";
            while (true) {
                System.out.println("Введите название ингредиента");
                ingredientName = scanner.next().trim().toUpperCase();
                String finalIngredientName = ingredientName;
                if (allIngredient.stream().anyMatch(ingredient -> ingredient.getName().equals(finalIngredientName))) {
                    System.out.println("Ингредиент с таким именем уже есть");
                } else {
                    break;
                }
            }

            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            System.out.println("Введите единицу измерения");
            ingredient.setUnit(scanner.next().trim().toUpperCase());

            ingredient = jdbcRecipe.addIngredient(ingredient);
            return ingredient;
        }
        return null;
    }

    private void showAllRecipes() {
        var allRecipes = jdbcRecipe.findAllRecipes();
        if (allRecipes.isEmpty()) {
            System.out.println("нет рецептов");
        }
        for (var recipe : allRecipes) {
            System.out.println(recipe.getId() + ". " + recipe.getName());
        }
    }
}
