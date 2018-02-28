package com.valencra.recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeUserService implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void delete(User user) {
    userRepository.delete(user);
  }
}
