package com.valencra.recipes.service;

import com.valencra.recipes.model.Ingredient;

import java.util.List;

public interface IngredientService {
  void save(Ingredient ingredient);
  List<Ingredient> findAll();
  Ingredient findOne(Long id);
  Ingredient findByName(String name);
  void delete(Ingredient ingredient);
}
