package com.valencra.recipes.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.valencra.recipes.model.User;
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
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private MockMvc mockMvc;

  private static final long TEST_ID = 1L;
  private static final String TEST_NAME = "Test";
  private static final String TEST_USERNAME = "user";
  private static final String TEST_PASSWORD = "password";
  private static final String TEST_ROLE = "USER";
  private static final User TEST_USER = new User();
  static {
    TEST_USER.setId(TEST_ID);
    TEST_USER.setName(TEST_NAME);
    TEST_USER.setUsername(TEST_USERNAME);
    TEST_USER.setPassword(TEST_PASSWORD);
    TEST_USER.setRoles(new String[]{TEST_ROLE});
  }

  private static final List<GrantedAuthority> TEST_GRANTED_AUTHORITY_LIST =
      new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("STANDARD")));
  private static final Authentication TEST_AUTHENTICATION =
      new UsernamePasswordAuthenticationToken(TEST_USERNAME, TEST_PASSWORD, TEST_GRANTED_AUTHORITY_LIST);

  @Before
  public void startMocks(){
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("classpath:/templates/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders
        .standaloneSetup(userController)
        .setViewResolvers(viewResolver)
        .build();

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getSignupRendersSignupForm() throws Exception {
    mockMvc.perform(get("/signup"))
        .andExpect(status().isOk())
        .andExpect(view().name("signup"))
        .andExpect(model().attribute("user", new User()));
  }

  @Test
  public void postSignupAddsUser() throws Exception  {
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(null);

    mockMvc.perform(post("/signup")
        .param("name", TEST_USER.getName())
        .param("username", TEST_USER.getUsername())
        .param("password", TEST_USER.getPassword())
        .param("roles", TEST_USER.getRoles()))
        .andExpect(redirectedUrl("/login"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void getLoginRendersLoginForm() throws Exception {
    mockMvc.perform(get("/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attribute("user", new User()));
  }

  @Test
  public void currentUserProfileRenders() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);

    mockMvc.perform(get("/profile")
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("profile"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("viewedUser", TEST_USER))
        .andExpect(model().attribute("authorized", true));
  }

  @Test
  public void userProfileRenders() throws Exception {
    SecurityContextHolder.getContext().setAuthentication(TEST_AUTHENTICATION);
    when(userService.findByUsername(TEST_USERNAME)).thenReturn(TEST_USER);
    when(userService.findOne(TEST_ID)).thenReturn(TEST_USER);

    mockMvc.perform(get(String.format("/users/%d", TEST_ID))
        .principal(TEST_AUTHENTICATION))
        .andExpect(status().isOk())
        .andExpect(view().name("profile"))
        .andExpect(model().attribute("user", TEST_USER))
        .andExpect(model().attribute("viewedUser", TEST_USER));
  }
}