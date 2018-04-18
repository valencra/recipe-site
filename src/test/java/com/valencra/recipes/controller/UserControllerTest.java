package com.valencra.recipes.controller;

import static org.junit.Assert.*;
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

  private static final String TEST_USERNAME = "user";
  private static final String TEST_PASSWORD = "password";
  private static final String TEST_ROLE = "USER";
  private static final User TEST_USER =
      new User("Test", TEST_USERNAME, TEST_PASSWORD, new String[]{TEST_ROLE});

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
}