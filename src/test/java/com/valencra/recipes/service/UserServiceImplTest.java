package com.valencra.recipes.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.valencra.recipes.model.User;
import com.valencra.recipes.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  public static final User
      TEST_USER =
      new User("Test", "test_user", "password", new String[]{"ROLE_USER"});

  @Test
  public void findAllReturnsAllUsers() throws Exception {
    final List<User> expectedUsers = Arrays.asList(TEST_USER);
    when(userRepository.findAll()).thenReturn(expectedUsers);

    final List<User> actualUsers = userService.findAll();

    assertTrue(actualUsers.equals(expectedUsers));
  }

  @Test
  public void findOneReturnsUserById() throws Exception {
    final long testId = 1l;
    when(userRepository.findOne(testId)).thenReturn(TEST_USER);

    final User actualUser = userService.findOne(testId);

    assertTrue(actualUser.equals(TEST_USER));
  }

  @Test
  public void findByUsernameReturnsUserByUsername() throws Exception {
    final String testUsername = "test_user";
    when(userRepository.findByUsername(testUsername)).thenReturn(TEST_USER);

    final User actualUser = userService.findByUsername(testUsername);

    assertTrue(actualUser.equals(TEST_USER));
  }
}