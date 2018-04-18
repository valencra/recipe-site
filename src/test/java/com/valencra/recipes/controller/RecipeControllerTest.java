package com.valencra.recipes.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.valencra.recipes.enums.Category;
import com.valencra.recipes.model.Ingredient;
import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.Step;
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
import org.springframework.mock.web.MockMultipartFile;
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
import javax.servlet.http.HttpServletRequest;

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

  private static Long TEST_RECIPE_1_ID = 1L;
  private static final String TEST_RECIPE_1_CATEGORY = "test_category";
  private static final Recipe TEST_RECIPE_1 = new Recipe();
  private static final Ingredient TEST_RECIPE_1_INGREDIENT_1 = new Ingredient();
  private static final Step TEST_RECIPE_1_STEP_1 = new Step();
  static {
    TEST_RECIPE_1.setName("test_name");
    TEST_RECIPE_1.setDescription("test_description");
    TEST_RECIPE_1.setCategory("test_category");
    TEST_RECIPE_1.setImage(new byte[0]);
    TEST_RECIPE_1.setPreparationTime(1);
    TEST_RECIPE_1.setCookingTime(1);
    TEST_RECIPE_1.setAuthor(TEST_USER);
    TEST_RECIPE_1.setId(TEST_RECIPE_1_ID);

    TEST_RECIPE_1_INGREDIENT_1.setName("test_ingredient_name");
    TEST_RECIPE_1_INGREDIENT_1.setCondition("test_ingredient_condition");
    TEST_RECIPE_1_INGREDIENT_1.setQuantity(1.0);
    TEST_RECIPE_1_INGREDIENT_1.setId(1L);
    TEST_RECIPE_1.addIngredient(TEST_RECIPE_1_INGREDIENT_1);

    TEST_RECIPE_1_STEP_1.setName("test_step_name");
    TEST_RECIPE_1_STEP_1.setId(1L);
    TEST_RECIPE_1.addStep(TEST_RECIPE_1_STEP_1);
  }


  private static final String TEST_RECIPE_2_NAME = "test_name_2";
  private static final String TEST_RECIPE_2_DESCRIPTION = "test_description_2";
  private static final String TEST_RECIPE_2_CATEGORY = "test_category_2";
  private static final String TEST_RECIPE_2_INGREDIENT = "test_ingredient_2";
  private static final Recipe TEST_RECIPE_2 = new Recipe(
      TEST_RECIPE_2_NAME,
      TEST_RECIPE_2_DESCRIPTION,
      TEST_RECIPE_2_CATEGORY,
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
    when(recipeService.findOne(TEST_RECIPE_1_ID)).thenReturn(TEST_RECIPE_1);

    mockMvc.perform(get(String.format("/recipes/%d", TEST_RECIPE_1_ID.intValue())).principal(TEST_AUTHENTICATION))
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
        "", "", TEST_RECIPE_1_CATEGORY))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", Arrays.asList(TEST_RECIPE_1)));
  }

  @Test
  public void recipeSearchByNameQueryReturnsResultsCorrectly() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findAll()).thenReturn(TEST_RECIPES);
    when(recipeService.findByNameContaining(TEST_RECIPE_2_NAME)).thenReturn(Arrays.asList(TEST_RECIPE_2));

    mockMvc.perform(get(String.format("/recipes/search?query=%s&filter=%s&category=%s",
        TEST_RECIPE_2_NAME, "name", ""))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", Arrays.asList(TEST_RECIPE_2)));
  }

  @Test
  public void recipeSearchByDescriptionQueryReturnsResultsCorrectly() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findAll()).thenReturn(TEST_RECIPES);
    when(recipeService.findByDescriptionContaining(TEST_RECIPE_2_DESCRIPTION)).thenReturn(Arrays.asList(TEST_RECIPE_2));

    mockMvc.perform(get(String.format("/recipes/search?query=%s&filter=%s&category=%s",
        TEST_RECIPE_2_DESCRIPTION, "description", ""))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", Arrays.asList(TEST_RECIPE_2)));
  }

  @Test
  public void recipeSearchByIngredientQueryReturnsResultsCorrectly() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findAll()).thenReturn(TEST_RECIPES);
    when(recipeService.findByIngredient(TEST_RECIPE_2_INGREDIENT)).thenReturn(Arrays.asList(TEST_RECIPE_2));

    mockMvc.perform(get(String.format("/recipes/search?query=%s&filter=%s&category=%s",
        TEST_RECIPE_2_INGREDIENT, "ingredient", ""))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipes", Arrays.asList(TEST_RECIPE_2)));
  }

  @Test
  public void recipeCreateFormRenders() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);

    mockMvc.perform(get("/recipes/create-form").principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("edit"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("categories", Category.values()))
        .andExpect(model().attribute("redirect", "/"))
        .andExpect(model().attribute("heading", "Create Recipe"))
        .andExpect(model().attribute("action", "/recipes/create"))
        .andExpect(model().attribute("submit", "Create"));
  }

  @Test
  public void createRecipeAddsNewRecipe() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.save(any(Recipe.class), eq(TEST_USER))).thenReturn(true);

    mockMvc.perform(fileUpload("/recipes/create")
        .file(new MockMultipartFile("imageFile", "TEST_IMAGE_AS_STRING".getBytes()))
        .param("id", TEST_RECIPE_1.getId().toString())
        .param("name", TEST_RECIPE_1.getName())
        .param("description", TEST_RECIPE_1.getDescription())
        .param("category", TEST_RECIPE_1.getCategory())
        .param("preparationTime", TEST_RECIPE_1.getPreparationTime().toString())
        .param("cookingTime", TEST_RECIPE_1.getCookingTime().toString())
        .param("ingredients[0].id", TEST_RECIPE_1_INGREDIENT_1.getId().toString())
        .param("ingredients[0].name", TEST_RECIPE_1_INGREDIENT_1.getName())
        .param("ingredients[0].condition", TEST_RECIPE_1_INGREDIENT_1.getCondition())
        .param("ingredients[0].quantity", TEST_RECIPE_1_INGREDIENT_1.getQuantity().toString())
        .param("steps[0].id", TEST_RECIPE_1_STEP_1.getId().toString())
        .param("steps[0].name", TEST_RECIPE_1_STEP_1.getName())
        .principal(TEST_AUTHENTICATION))
        .andExpect(redirectedUrl(String.format("/recipes/%d", TEST_RECIPE_1.getId())))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void recipeEditFormRenders() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findOne(TEST_RECIPE_1_ID)).thenReturn(TEST_RECIPE_1);

    mockMvc.perform(get(String.format("/recipes/%d/edit-form", TEST_RECIPE_1_ID.intValue())).principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("edit"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("recipe", TEST_RECIPE_1))
        .andExpect(model().attribute("categories", Category.values()))
        .andExpect(model().attribute("redirect", String.format("/recipes/%d", TEST_RECIPE_1_ID.intValue())))
        .andExpect(model().attribute("heading", "Edit Recipe"))
        .andExpect(model().attribute("action", String.format("/recipes/%d/edit", TEST_RECIPE_1_ID.intValue())))
        .andExpect(model().attribute("submit", "Edit"));
  }

  @Test
  public void editRecipeEditsRecipe() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(recipeService.findOne(TEST_RECIPE_1_ID)).thenReturn(TEST_RECIPE_1);
    when(recipeService.save(any(Recipe.class), eq(TEST_USER))).thenReturn(true);

    mockMvc.perform(fileUpload(String.format("/recipes/%d/edit", TEST_RECIPE_1.getId()))
        .file(new MockMultipartFile("imageFile", "TEST_IMAGE_AS_STRING".getBytes()))
        .param("id", TEST_RECIPE_1.getId().toString())
        .param("name", TEST_RECIPE_1.getName())
        .param("description", TEST_RECIPE_1.getDescription())
        .param("category", TEST_RECIPE_1.getCategory())
        .param("preparationTime", TEST_RECIPE_1.getPreparationTime().toString())
        .param("cookingTime", TEST_RECIPE_1.getCookingTime().toString())
        .param("ingredients[0].id", TEST_RECIPE_1_INGREDIENT_1.getId().toString())
        .param("ingredients[0].name", TEST_RECIPE_1_INGREDIENT_1.getName())
        .param("ingredients[0].condition", TEST_RECIPE_1_INGREDIENT_1.getCondition())
        .param("ingredients[0].quantity", TEST_RECIPE_1_INGREDIENT_1.getQuantity().toString())
        .param("steps[0].id", TEST_RECIPE_1_STEP_1.getId().toString())
        .param("steps[0].name", TEST_RECIPE_1_STEP_1.getName())
        .principal(TEST_AUTHENTICATION))
        .andExpect(redirectedUrl(String.format("/recipes/%d", TEST_RECIPE_1.getId())))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void favoriteRecipeTogglesFavorite() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findOne(TEST_RECIPE_1_ID)).thenReturn(TEST_RECIPE_1);
    HttpServletRequest request = mock(HttpServletRequest.class);

    mockMvc.perform(post(String.format("/recipes/%d/favorite", TEST_RECIPE_1_ID.intValue()))
        .header("Referer", String.format("/recipes/%d", TEST_RECIPE_1.getId()))
        .principal(TEST_AUTHENTICATION))
        .andExpect(redirectedUrl(String.format("/recipes/%d", TEST_RECIPE_1.getId())))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void deleteRecipeDeletesRecipe() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(recipeService.findOne(TEST_RECIPE_1_ID)).thenReturn(TEST_RECIPE_1);
    when(recipeService.delete(TEST_RECIPE_1, TEST_USER)).thenReturn(true);

    mockMvc.perform(post(String.format("/recipes/%d/delete", TEST_RECIPE_1_ID.intValue()))
        .principal(TEST_AUTHENTICATION))
        .andExpect(redirectedUrl("/"))
        .andExpect(status().is3xxRedirection());
  }
}