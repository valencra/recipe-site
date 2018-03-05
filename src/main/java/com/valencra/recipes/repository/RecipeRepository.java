package com.valencra.recipes.repository;

import com.valencra.recipes.enums.Category;
import com.valencra.recipes.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
  @RestResource(rel = "nameContains", path = "nameContains")
  List<Recipe> findByNameContaining(@Param("name") String name);

  @RestResource(rel = "descriptionContains", path = "descriptionContains")
  List<Recipe> findByDescriptionContaining(@Param("description") String description);

  @RestResource(rel = "category", path = "category")
  List<Recipe> findByCategory(@Param("category") String category);

  @RestResource(rel = "ingredientId", path = "ingredientId")
  @Query(
      value = "SELECT DISTINCT recipe_id FROM ingredient WHERE id = :ingredientId",
      nativeQuery = true
  )
  List<BigInteger> findByIngredient(@Param("ingredientId") Long ingredientId);
}
