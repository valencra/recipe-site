package com.valencra.recipes.controller;

import com.valencra.recipes.model.User;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/signup")
  public String signupForm(Model model) {
    model.addAttribute("user", new User());
    return "signup";
  }

  @PostMapping("/signup")
  public String signup(User user) {
    // only create user if it does not exist
    if (userService.findByUsername(user.getUsername()) == null) {
      user.setRoles(new String[] {"ROLE_USER"});
      userService.save(user);
      return "redirect:/login";
    }
    else {
      return "redirect:/signup";
    }
  }

  @GetMapping("/login")
  public String loginForm(Model model) {
    model.addAttribute("user", new User());
    return "login";
  }

  @GetMapping("/profile")
  public String currentUserProfile(Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    model.addAttribute("viewedUser", user);
    model.addAttribute("authorized", true);
    return "profile";
  }

  @GetMapping("/users/{id}")
  public String userProfile(@PathVariable Long id, Model model, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated())
    {
      return "redirect:/login";
    }
    User user = userService.findByUsername(authentication.getName());
    model.addAttribute("user", user);
    User viewedUser = userService.findOne(id);
    model.addAttribute("viewedUser", viewedUser);
    return "profile";
  }
}
