package com.valencra.recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeRecipeUserServiceImpl implements RecipeUserService {

  @Autowired
  private RecipeUserRepository recipeUserRepository;

  @Override
  public void save(RecipeUser recipeUser) {
    recipeUserRepository.save(recipeUser);
  }

  @Override
  public List<RecipeUser> findAll() {
    return recipeUserRepository.findAll();
  }

  @Override
  public RecipeUser findById(Long id) {
    return recipeUserRepository.findById(id);
  }

  @Override
  public RecipeUser findByUsername(String username) {
    return recipeUserRepository.findByUsername(username);
  }

  @Override
  public void delete(RecipeUser recipeUser) {
    recipeUserRepository.delete(recipeUser);
  }
}
