package com.valencra.recipes.service;

import com.valencra.recipes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RecipesAppUserDetailsService implements UserDetailsService{

  @Autowired
  UserService userService;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User recipeUser = userService.findByUsername(username);

    if (recipeUser == null) {
      throw new UsernameNotFoundException("Unable to find user with username " + username);
    }

    org.springframework.security.core.userdetails.User
        user = new org.springframework.security.core.userdetails.User(
        recipeUser.getUsername(),
        recipeUser.getPassword(),
        AuthorityUtils.createAuthorityList(recipeUser.getRoles()));

    return user;
  }
}