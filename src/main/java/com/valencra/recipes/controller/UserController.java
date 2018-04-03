package com.valencra.recipes.controller;

import com.valencra.recipes.model.User;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public String signup(Model model) {
    model.addAttribute("user", new User());
    return "signup";
  }

  @PostMapping("/users")
  public String createUser(User user) {
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
  public String login(Model model) {
    model.addAttribute("user", new User());
    return "login";
  }

  @GetMapping("/profile")
  public String currentUserProfile(Model model) {
    User currentUser = (User) model.asMap().get("currentUser");
    model.addAttribute("user", currentUser);
    model.addAttribute("authorized", true);
    return "profile";
  }

  @GetMapping("/users/{id}")
  public String userProfile(@PathVariable Long id, Model model) {
    User queriedUser = userService.findOne(id);
    model.addAttribute("user", queriedUser);

    User currentUser = (User) model.asMap().get("currentUser");
    if (currentUser != null && currentUser.isAdmin()) {
      model.addAttribute("authorized", true);
    }

    return "profile";
  }
}
