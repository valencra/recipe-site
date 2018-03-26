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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RecipeController {
  @Autowired
  private UserService userService;

  @Autowired
  private IngredientService ingredientService;

  @Autowired
  private RecipeService recipeService;

  @GetMapping("/")
  public String recipeIndex(Model model) {
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
}
