package com.valencra.recipes.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.valencra.recipes.RecipesApp;
import com.valencra.recipes.config.SecurityConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

  public static final String TEST_USERNAME = "user";

  public static final String TEST_PASSWORD = "password";

  public static final String TEST_ROLE = "USER";

  public static final User TEST_USER =
      new User("Test", TEST_USERNAME, TEST_PASSWORD, new String[]{TEST_ROLE});

  private final Recipe TEST_RECIPE = new Recipe(
      "test_name",
      "test_description",
      "test_category",
      new byte[0],
      1,
      1,
      TEST_USER
  );

  @Before
  public void startMocks(){
    mockMvc = MockMvcBuilders
        .standaloneSetup(recipeController)
        .build();

    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser(username = TEST_USERNAME, password = TEST_PASSWORD, roles = TEST_ROLE)
  public void recipeIndexDisplaysAllRecipes() throws Exception {
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    final List<Recipe> expectedRecipes = Arrays.asList(TEST_RECIPE);
    when(recipeService.findAll()).thenReturn(expectedRecipes);

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", expectedRecipes));
  }
}