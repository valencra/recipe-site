package com.valencra.recipes.repository;

import com.valencra.recipes.model.RecipeUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeUserRepository extends CrudRepository<RecipeUser, Long> {
  List<RecipeUser> findAll();
  RecipeUser findByUsername(String username);
}
