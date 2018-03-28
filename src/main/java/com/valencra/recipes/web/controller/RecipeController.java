package com.valencra.recipes.web.controller;

import com.valencra.recipes.enums.Category;
import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;
import com.valencra.recipes.service.IngredientService;
import com.valencra.recipes.service.RecipeService;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
  @Autowired
  private UserService userService;

  @Autowired
  private IngredientService ingredientService;

  @Autowired
  private RecipeService recipeService;

  @GetMapping("/")
  public String recipesIndex(Model model) {
    User currentUser = (User) model.asMap().get("currentUser");
    if (currentUser != null) {
      model.addAttribute("authorized", currentUser.isAdmin());
    }

    List<Recipe> recipes = recipeService.findAll();
    model.addAttribute("recipes", recipes);
    model.addAttribute("categories", Category.values());

    return "index";
  }

  @GetMapping("/recipes/{id}")
  public String recipeDetail(Model model, @PathVariable Long id) {
    Recipe recipe = recipeService.findOne(id);
    model.addAttribute("recipe", recipe);
    return "detail";
  }

  @GetMapping("/recipes/search")
  public String searchRecipes(
      @RequestParam(required = false) String query,
      @RequestParam(required = false) String field,
      @RequestParam(required = false) String category,
      Model model) {
    List<Recipe> recipes = recipeService.findAll();

    if (query != null && query.trim() != "") {
      switch (field) {
        case "name":
          recipes = recipeService.findByNameContaining(query);
          break;
        case "description":
          recipes = recipeService.findByDescriptionContaining(query);
          break;
        case "ingredient":
          recipes = recipeService.findByIngredient(query);
          break;
      }

      if (category != null && category.trim() != "") {
        recipes = recipes.stream()
            .filter(recipe -> recipe.getCategory().equals(category))
            .collect(Collectors.toList());
      }
    }

    model.addAttribute("recipes", recipes);
    model.addAttribute("categories", Category.values());

    return "index";

  }

  @GetMapping("/recipes/create-form")
  public String createRecipeForm(Model model) {
    Recipe recipe = new Recipe();

    model.addAttribute("recipe", recipe);
    model.addAttribute("categories", Category.values());
    model.addAttribute("redirect", "/");
    model.addAttribute("heading", "Create Recipe");
    model.addAttribute("action", "/recipes/create");
    model.addAttribute("submit", "Create");

    return "edit";
  }

  @PostMapping("/recipes/create")
  public String createRecipe(Recipe recipe, Model model) {
    User currentUser = (User) model.asMap().get("currentUser");
    recipe.setAuthor(currentUser);
    recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));

    recipeService.save(recipe, currentUser);
    userService.save(currentUser);

    return "redirect:/recipes/" + recipe.getId();
  }

  @GetMapping("/recipes/{id}/edit-form")
  public String editRecipeForm(Model model, @PathVariable Long id) {
    Recipe recipe = recipeService.findOne(id);

    model.addAttribute("recipe", recipe);
    model.addAttribute("categories", Category.values());
    model.addAttribute("redirect", "/recipes/" + id);
    model.addAttribute("heading", "Edit Recipe");
    model.addAttribute("action", "/recipes/" + id + "/edit");
    model.addAttribute("submit", "Edit");

    return "edit";
  }

  @PostMapping(value = "/recipes/{id}/edit")
  public String editRecipe(Recipe recipe, @PathVariable Long id) {
    User author = recipeService.findOne(id).getAuthor();
    recipe.setAuthor(author);
    recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));

    recipeService.save(recipe, author);
    userService.save(author);

    return "redirect:/recipes/" + recipe.getId();
  }

  @DeleteMapping(path = "/recipes/{id}/delete")
  public String deleteRecipe(Model model, @PathVariable Long id) {
    User currentUser = (User) model.asMap().get("currentUser");
    Recipe recipe = recipeService.findOne(id);
    recipeService.delete(recipe, currentUser);
    return "redirect:/";
  }
}
