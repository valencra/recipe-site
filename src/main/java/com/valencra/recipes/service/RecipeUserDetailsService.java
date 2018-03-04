package com.valencra.recipes.service;

import com.valencra.recipes.model.RecipeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RecipeUserDetailsService implements UserDetailsService{

  @Autowired
  RecipeUserService recipeUserService;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    RecipeUser recipeUser = recipeUserService.findByUsername(username);

    if (recipeUser == null) {
      throw new UsernameNotFoundException("Unable to find user with username " + username);
    }

    User user = new User(
        recipeUser.getUsername(),
        recipeUser.getPassword(),
        AuthorityUtils.createAuthorityList(recipeUser.getRoles()));

    return user;
  }
}