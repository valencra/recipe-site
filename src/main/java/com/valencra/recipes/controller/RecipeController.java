package com.valencra.recipes.controller;

import com.valencra.recipes.enums.Category;
import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.Step;
import com.valencra.recipes.model.User;
import com.valencra.recipes.service.IngredientService;
import com.valencra.recipes.service.RecipeService;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RecipeController {
  public static final List<String> SEARCH_FILTERS =
      Arrays.asList("name", "description", "ingredient");

  @Autowired
  private UserService userService;

  @Autowired
  private IngredientService ingredientService;

  @Autowired
  private RecipeService recipeService;

  @GetMapping("/")
  public String recipesIndex(Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);

    List<Recipe> recipes = recipeService.findAll();
    model.addAttribute("recipes", recipes);
    model.addAttribute("categories", Category.values());
    model.addAttribute("filters", SEARCH_FILTERS);

    return "index";
  }

  @GetMapping("/recipes/{id}")
  public String recipeDetail(@PathVariable Long id, Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);

    Recipe recipe = recipeService.findOne(id);
    model.addAttribute("recipe", recipe);
    return "detail";
  }

  @GetMapping(value = "/recipes/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody byte[] recipeImage(@PathVariable Long id) {
    byte[] image = recipeService.findOne(id).getImage();
    return image;
  }

  @PostMapping("/recipes/{id}/favorite")
  public String favoriteRecipe(@PathVariable Long id, Model model, HttpServletRequest request, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    Recipe recipe = recipeService.findOne(id);

    if (user.getFavoriteRecipes().contains(recipe)) {
      user.removeFavoriteRecipe(recipe);
    }
    else {
      user.addFavoriteRecipe(recipe);
    }

    userService.save(user);

    return "redirect:" + request.getHeader("Referer");
  }

  @GetMapping("/recipes/search")
  public String searchRecipes(
      @RequestParam(required = false) String query,
      @RequestParam(required = false) String filter,
      @RequestParam(required = false) String category,
      Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    List<Recipe> recipes = recipeService.findAll();

    if (query != null && !query.trim().equals("")) {
      switch (filter) {
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
    }

    if (category != null && !category.trim().equals("")) {
      recipes = recipes.stream()
          .filter(recipe -> recipe.getCategory().equals(category))
          .collect(Collectors.toList());
    }

    model.addAttribute("recipes", recipes);
    model.addAttribute("categories", Category.values());
    model.addAttribute("filters", SEARCH_FILTERS);

    return "index";

  }

  @GetMapping("/recipes/create-form")
  public String createRecipeForm(Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    Recipe recipe = new Recipe();
    recipe.getSteps().add(new Step());
    recipe.getIngredients().add(new Ingredient());

    model.addAttribute("recipe", recipe);
    model.addAttribute("categories", Category.values());
    model.addAttribute("redirect", "/");
    model.addAttribute("heading", "Create Recipe");
    model.addAttribute("action", "/recipes/create");
    model.addAttribute("submit", "Create");

    return "edit";
  }

  @PostMapping("/recipes/create")
  public String createRecipe(@RequestParam MultipartFile imageFile, Recipe recipe, Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    recipe.setAuthor(user);
    try {
      byte[] image = imageFile.getBytes();
      recipe.setImage(image);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    recipeService.save(recipe, user);
    userService.save(user);

    return "redirect:/recipes/" + recipe.getId();
  }

  @GetMapping("/recipes/{id}/edit-form")
  public String editRecipeForm(@PathVariable Long id, Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);

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
  public String editRecipe(@PathVariable Long id, @RequestParam MultipartFile imageFile, Recipe recipe) {
    User author = recipeService.findOne(id).getAuthor();
    recipe.setAuthor(author);
    try {
      byte[] image = imageFile.getBytes();
      recipe.setImage(image);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    recipeService.save(recipe, author);
    userService.save(author);

    return "redirect:/recipes/" + recipe.getId();
  }

  @PostMapping(path = "/recipes/{id}/delete")
  public String deleteRecipe(@PathVariable Long id, Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    Recipe recipe = recipeService.findOne(id);
    recipeService.delete(recipe, user);
    return "redirect:/";
  }
}
