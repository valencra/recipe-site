package com.valencra.recipes.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.valencra.recipes.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class RecipesAppUserDetailsServiceTest {
  @Mock
  private UserService userService = new UserServiceImpl();

  @InjectMocks
  private RecipesAppUserDetailsService recipesAppUserDetailsService;

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsernameThrowsExceptionWhenUserNotFound() {
    String fakeUser = "fake_user";
    recipesAppUserDetailsService.loadUserByUsername(fakeUser);
  }

  public void loadUserByUsernameLoadsByUsername() {
    String expectedUsername = "test_user";
    User expectedUser = new User("Test User", expectedUsername, "p@ssw0rd", new String[] {"ROLE_USER"});
    when(userService.findByUsername(expectedUsername)).thenReturn(expectedUser);

    String actualUsername = recipesAppUserDetailsService.loadUserByUsername(expectedUsername).getUsername();

    assertEquals(expectedUsername, actualUsername);
  }
}