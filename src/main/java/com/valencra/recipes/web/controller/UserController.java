package com.valencra.recipes.web.controller;

import com.valencra.recipes.model.User;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
