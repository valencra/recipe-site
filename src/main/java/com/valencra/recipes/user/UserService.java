package com.valencra.recipes.user;

import java.util.List;

public interface UserService {
  void save(User user);
  List<User> findAll();
  User findById(Long id);
  User findByUsername(String username);
  void delete(User user);
}