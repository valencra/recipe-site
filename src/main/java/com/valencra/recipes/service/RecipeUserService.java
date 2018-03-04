package com.valencra.recipes.service;

import com.valencra.recipes.model.RecipeUser;

import java.util.List;

public interface RecipeUserService {
  void save(RecipeUser recipeUser);
  List<RecipeUser> findAll();
  RecipeUser findOne(Long id);
  RecipeUser findByUsername(String username);
  void delete(RecipeUser recipeUser);
}