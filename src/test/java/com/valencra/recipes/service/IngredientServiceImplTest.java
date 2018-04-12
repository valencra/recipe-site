package com.valencra.recipes.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.repository.IngredientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {
  @Mock
  private IngredientRepository ingredientRepository;

  @InjectMocks
  private IngredientService ingredientService = new IngredientServiceImpl();

  @Test
  public void findAllFindsAll() {
    List<Ingredient> expectedIngredients = Arrays.asList(new Ingredient("Ingredient", "Condition", 1.0));
    when(ingredientRepository.findAll())
        .thenReturn(expectedIngredients);

    List<Ingredient> actualIngredients = ingredientService.findAll();

    assertEquals(expectedIngredients, actualIngredients);
  }

  @Test
  public void findOneFindOneFindsById() {
    Ingredient expectedIngredient = new Ingredient("Ingredient", "Condition", 1.0);
    long testId = 1L;
    when(ingredientRepository.findOne(testId))
        .thenReturn(expectedIngredient);

    Ingredient actualIngredient = ingredientService.findOne(testId);

    assertEquals(expectedIngredient, actualIngredient);
  }

  @Test
  public void findByNameFindsByName() {
    Ingredient expectedIngredient = new Ingredient("Ingredient", "Condition", 1.0);
    String testName = "Ingredient";
    when(ingredientRepository.findByName(testName))
        .thenReturn(expectedIngredient);

    Ingredient actualIngredient = ingredientService.findByName(testName);

    assertEquals(expectedIngredient, actualIngredient);
  }
}