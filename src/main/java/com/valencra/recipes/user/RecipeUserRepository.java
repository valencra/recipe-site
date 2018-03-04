package com.valencra.recipes.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeUserRepository extends CrudRepository<RecipeUser, Long> {
  List<RecipeUser> findAll();
  RecipeUser findByUsername(String username);
}
