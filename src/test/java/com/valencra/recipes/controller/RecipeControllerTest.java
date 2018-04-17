package com.valencra.recipes.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;
import com.valencra.recipes.service.IngredientService;
import com.valencra.recipes.service.RecipeService;
import com.valencra.recipes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private IngredientService ingredientService;

  @Mock
  private RecipeService recipeService;

  @InjectMocks
  private RecipeController recipeController;

  private MockMvc mockMvc;

  private static final String TEST_USERNAME = "user";

  private static final String TEST_PASSWORD = "password";

  private static final String TEST_ROLE = "USER";

  private static final User TEST_USER =
      new User("Test", TEST_USERNAME, TEST_PASSWORD, new String[]{TEST_ROLE});

  private static Long TEST_RECIPE_ID = 1L;

  private static final Recipe TEST_RECIPE_1 = new Recipe(
      "test_name",
      "test_description",
      "test_category",
      new byte[0],
      1,
      1,
      TEST_USER
  );

  private static final Recipe TEST_RECIPE_2 = new Recipe(
      "test_name_2",
      "test_description_2",
      "test_category_2",
      new byte[0],
      1,
      1,
      TEST_USER
  );

  private static final List<Recipe> TEST_RECIPES =
      Arrays.asList(TEST_RECIPE_1, TEST_RECIPE_2);

  private static final List<GrantedAuthority> TEST_GRANTED_AUTHORITY_LIST =
      new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("STANDARD")));

  private static final Authentication TEST_AUTHENTICATION =
      new UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_GRANTED_AUTHORITY_LIST);

  @Before
  public void startMocks(){
    mockMvc = MockMvcBuilders
        .standaloneSetup(recipeController)
        .build();

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void recipeIndexDisplaysAllRecipes() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findAll()).thenReturn(TEST_RECIPES);

    mockMvc.perform(get("/").principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", TEST_RECIPES));
  }

  @Test
  public void recipeDetailsDisplaysRecipeDetails() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findOne(TEST_RECIPE_ID)).thenReturn(TEST_RECIPE_1);

    mockMvc.perform(get(String.format("/recipes/%d", TEST_RECIPE_ID.intValue())).principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("detail"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipe", TEST_RECIPE_1));
  }

  @Test
  public void recipeSearchByCategoryReturnsResultsCorrectly() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findAll()).thenReturn(TEST_RECIPES);

    mockMvc.perform(get(String.format("/recipes/search?query=%s&filter=%s&category=%s",
        "", "", "test_category"))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", Arrays.asList(TEST_RECIPE_1)));
  }
}