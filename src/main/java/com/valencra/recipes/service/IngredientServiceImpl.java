package com.valencra.recipes.service;

import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
  @Autowired
  IngredientRepository ingredientRepository;

  @Override
  public void save(Ingredient ingredient) {
    ingredientRepository.save(ingredient);
  }

  @Override
  public List<Ingredient> findAll() {
    return (List<Ingredient>) ingredientRepository.findAll();
  }

  @Override
  public Ingredient findOne(Long id) {
    return ingredientRepository.findOne(id);
  }

  @Override
  public Ingredient findByName(String name) {
    return ingredientRepository.findByName(name);
  }

  @Override
  public void delete(Ingredient ingredient) {
    ingredientRepository.delete(ingredient);
  }
}
