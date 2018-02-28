package com.valencra.recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeRecipeUserServiceImpl implements RecipeUserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void save(RecipeUser recipeUser) {
    userRepository.save(recipeUser);
  }

  @Override
  public List<RecipeUser> findAll() {
    return userRepository.findAll();
  }

  @Override
  public RecipeUser findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public RecipeUser findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void delete(RecipeUser recipeUser) {
    userRepository.delete(recipeUser);
  }
}
