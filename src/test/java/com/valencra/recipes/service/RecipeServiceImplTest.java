package com.valencra.recipes.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;
import com.valencra.recipes.repository.IngredientRepository;
import com.valencra.recipes.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {
  @Mock
  private UserService userService;

  @Mock
  private IngredientRepository ingredientRepository;

  @Mock
  private RecipeRepository recipeRepository;

  @InjectMocks
  private RecipeService recipeService = new RecipeServiceImpl();

  private final Recipe TEST_RECIPE = new Recipe(
      "test_name",
      "test_description",
      "test_category",
      new byte[0],
      1,
      1,
      new User()
  );

  @Test
  public void findAllReturnsAllRecipes() {
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);
    when(recipeRepository.findAll()).thenReturn(expectedRecipes);

    List<Recipe> actualRecipes = recipeService.findAll();

    assertTrue(expectedRecipes.equals(actualRecipes));
  }

  @Test
  public void findOneReturnsById() {
    final long testId = 1L;
    when(recipeRepository.findOne(testId)).thenReturn(TEST_RECIPE);

    Recipe actualRecipe = recipeService.findOne(testId);

    assertTrue(TEST_RECIPE.equals(actualRecipe));
  }

  @Test
  public void findByNameContainingReturnsByNameContaining() {
    final String testName = "test_name";
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);
    when(recipeRepository.findByNameContaining(testName)).thenReturn(expectedRecipes);

    List<Recipe> actualRecipes = recipeService.findByNameContaining(testName);

    assertTrue(expectedRecipes.equals(actualRecipes));
  }

  @Test
  public void findByDescriptionContainingReturnsByDescriptionContaining() {
    final String testDescription = "test_description";
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);
    when(recipeRepository.findByDescriptionContaining(testDescription)).thenReturn(expectedRecipes);

    List<Recipe> actualRecipes = recipeService.findByDescriptionContaining(testDescription);

    assertTrue(expectedRecipes.equals(actualRecipes));
  }

  @Test
  public void findByCategoryReturnsByCategory() {
    final String testCategory = "test_category";
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);
    when(recipeRepository.findByCategory(testCategory)).thenReturn(expectedRecipes);

    List<Recipe> actualRecipes = recipeService.findByCategory(testCategory);

    assertTrue(expectedRecipes.equals(actualRecipes));
  }

  @Test
  public void findByIngredientReturnsByIngredient() {
    Ingredient testIngredient = new Ingredient();
    final long testIngredientId = 1L;
    testIngredient.setId(testIngredientId);
    final String testIngredientName = "test_ingredient";
    testIngredient.setName(testIngredientName);
    when(ingredientRepository.findByName(testIngredientName)).thenReturn(testIngredient);
    final long testRecipeId = 1L;
    when(recipeRepository.findByIngredient(testIngredientId)).thenReturn(Arrays.asList(BigInteger.valueOf(testRecipeId)));
    when(recipeRepository.findOne(testIngredientId)).thenReturn(TEST_RECIPE);
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);

    List<Recipe> actualRecipes = recipeService.findByIngredient(testIngredientName);

    assertTrue(expectedRecipes.equals(actualRecipes));
  }
}