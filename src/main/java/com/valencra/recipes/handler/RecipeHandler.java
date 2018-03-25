package com.valencra.recipes.handler;

import com.valencra.recipes.model.Recipe;
import com.valencra.recipes.model.User;
import com.valencra.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Recipe.class)
public class RecipeHandler {
  private final UserRepository userRepository;

  @Autowired
  public RecipeHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void setAuthorOnCreateOrSave(Recipe recipe) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username);
    recipe.setAuthor(user);
  }
}
