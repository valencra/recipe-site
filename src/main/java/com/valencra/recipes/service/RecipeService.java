package com.valencra.recipes.service;

import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;

import java.util.List;

public interface RecipeService {
  boolean save(Recipe recipe, User user);
  List<Recipe> findAll();
  Recipe findOne(Long id);
  List<Recipe> findByNameContaining(String name);
  List<Recipe> findByDescriptionContaining(String description);
  List<Recipe> findByCategory(String category);
  List<Recipe> findByIngredient(String ingredient);
  boolean delete(Recipe recipe, User user);
}
