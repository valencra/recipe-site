package com.valencra.recipes.repository;

import com.valencra.recipes.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
  List<User> findAll();
  User findByUsername(String username);
}
