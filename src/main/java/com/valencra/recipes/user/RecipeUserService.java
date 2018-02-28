package com.valencra.recipes.user;

import java.util.List;

public interface RecipeUserService {
  void save(RecipeUser recipeUser);
  List<RecipeUser> findAll();
  RecipeUser findById(Long id);
  RecipeUser findByUsername(String username);
  void delete(RecipeUser recipeUser);
}