package com.valencra.recipes.handler;

import com.valencra.recipes.model.User;
import com.valencra.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(basePackages = "com.valencra.recipes.web.controller")
public class UserHandler {
  public static final String USERNAME_NOT_FOUND_ERR_MSG = "Unable to find username";
  public static final String ACCESS_DENIED_ERR_MSG = "Login to use the app";

  @Autowired
  private UserService userService;

  @ModelAttribute("authenticatedUser")
  public User addAuthenticatedUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      String username = authentication.getName();
      User user = userService.findByUsername(username);
      if (user != null) {
        return user;
      }
      else {
        throw new UsernameNotFoundException(USERNAME_NOT_FOUND_ERR_MSG);
      }
    }
    else {
      throw new AccessDeniedException(ACCESS_DENIED_ERR_MSG);
    }
  }

  @ExceptionHandler(AccessDeniedException.class)
  public String redirectIfUserNotAuthenticated(RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("errorMessage", ACCESS_DENIED_ERR_MSG);
    return "redirect:/login";
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public String redirectIfUserNotFound(RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("errorMessage", USERNAME_NOT_FOUND_ERR_MSG);
    return "redirect:/login";
  }
}
