package com.valencra.recipes.service;

import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;
import com.valencra.recipes.repository.IngredientRepository;
import com.valencra.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

  @Autowired
  private UserService userService;

  @Autowired
  private RecipeRepository recipeRepository;

  @Autowired
  private IngredientRepository ingredientRepository;

  @Override
  public boolean save(Recipe recipe, User user) {
    if (user.isAdmin() || user.equals(recipe.getAuthor())) {
      recipeRepository.save(recipe);
      return true;
    }
    return false;
  }

  @Override
  public List<Recipe> findAll() {
    return (List<Recipe>) recipeRepository.findAll();
  }

  @Override
  public Recipe findOne(Long id) {
    return recipeRepository.findOne(id);
  }

  @Override
  public List<Recipe> findByNameContaining(String name) {
    return recipeRepository.findByNameContaining(name);
  }

  @Override
  public List<Recipe> findByDescriptionContaining(String description) {
    return recipeRepository.findByDescriptionContaining(description);
  }

  @Override
  public List<Recipe> findByCategory(String category) {
    return recipeRepository.findByCategory(category);
  }

  @Override
  public List<Recipe> findByIngredient(String ingredient) {
    Ingredient i = ingredientRepository.findByName(ingredient);
    List<Recipe> recipes = recipeRepository
        .findByIngredient(i.getId())
        .stream()
        .map(recipeIdx -> recipeRepository.findOne(recipeIdx.longValue()))
        .collect(Collectors.toList());
    return recipes;
  }

  @Override
  public boolean delete(Recipe recipe, User user) {
    final User author = recipe.getAuthor();
    if (user.isAdmin() || user.equals(author)) {
      // remove favourites
      userService.findAll().stream()
          .forEach(u -> {
            if (u.getFavoriteRecipes().contains(recipe)) {
              u.removeFavoriteRecipe(recipe);
              userService.save(u);
            }
          });
      // remove author - recipe association
      author.removeRecipe(recipe);
      userService.save(author);
      recipe.setAuthor(null);
      recipeRepository.save(recipe);
      recipeRepository.delete(recipe);
      return true;
    }
    return false;
  }
}
