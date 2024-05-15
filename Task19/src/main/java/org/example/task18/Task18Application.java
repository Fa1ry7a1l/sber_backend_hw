package org.example.task18;

import org.example.task18.entity.Ingredient;
import org.example.task18.entity.Recipe;
import org.example.task18.entity.RecipeIngredientLink;
import org.example.task18.service.IngredientService;
import org.example.task18.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Task18Application implements ApplicationRunner {

    private Scanner scanner = new Scanner(System.in);


    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    public static void main(String[] args) {
        SpringApplication.run(Task18Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {


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

        var searchResult = recipeService.findAllRecipesByName(name);
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
        var allRecipes = recipeService.findAllRecipes();
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
            recipeService.removeRecipe(selectedRecipe.get());
            System.out.println("Удален");
        }
    }

    private void showRecipe() {
        var allRecipes = recipeService.findAllRecipes();

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


        int i = 0;
        for (var ingredientLink : recipe.getRecipeIngredientLinkers()) {
            System.out.printf("%d. %s %.1f %s\n", i + 1, ingredientLink.getIngredient().getName(), ingredientLink.getAmount(), ingredientLink.getIngredient().getUnit());
            i++;
        }

        System.out.println("Вот и весь рецепт");
    }

    private void addRecipe() {
        boolean notCorrectRecipeName = true;
        String recipeName = "";
        while (notCorrectRecipeName) {
            System.out.println("Введите название рецепта");
            recipeName = scanner.next().trim().toUpperCase();
            var findedRecipes = recipeService.findAllRecipesByName(recipeName);
            String finalRecipeName = recipeName;
            if (findedRecipes.stream().noneMatch(recipe -> recipe.getName().equals(finalRecipeName))) {
                notCorrectRecipeName = false;
            } else {
                System.out.println("Рецепт с таким именем уже существует");
            }
        }
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe = recipeService.addRecipe(recipe);


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

        recipe.getRecipeIngredientLinkers().add(new RecipeIngredientLink(recipe, ingredient, amount));

        recipeService.updateRecipe(recipe);
    }

    private Ingredient addIngredient() {
        int answer;
        var allIngredient = ingredientService.findAllIngredients();
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

            ingredient = ingredientService.addIngredient(ingredient);
            return ingredient;
        }
        return null;
    }

    private void showAllRecipes() {
        var allRecipes = recipeService.findAllRecipes();
        if (allRecipes.isEmpty()) {
            System.out.println("нет рецептов");
        }
        for (var recipe : allRecipes) {
            System.out.println(recipe.getId() + ". " + recipe.getName());
        }
    }
}
