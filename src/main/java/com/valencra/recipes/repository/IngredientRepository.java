package com.valencra.recipes.repository;

import com.valencra.recipes.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
  Ingredient findByName(String name);
}
