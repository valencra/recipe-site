package com.valencra.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User extends BaseEntity {

  public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  private String name;

  private String username;

  @JsonIgnore
  private String password;

  @JsonIgnore
  private String[] roles;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Recipe> recipes;

  @ManyToMany
  private List<Recipe> favoriteRecipes;

  public User() {
    super();
    recipes = new ArrayList<>();
    favoriteRecipes = new ArrayList<>();
  }

  public User(String name, String username, String password, String[] roles) {
    this();
    this.name = name;
    this.username = username;
    setPassword(password);
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = PASSWORD_ENCODER.encode(password);
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }

  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }

  public void removeRecipe(Recipe recipe) {
    recipes.remove(recipe);
  }

  public List<Recipe> getFavoriteRecipes() {
    return favoriteRecipes;
  }

  public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
    this.favoriteRecipes = favoriteRecipes;
  }

  public void addFavoriteRecipe(Recipe recipe) {
    favoriteRecipes.add(recipe);
  }

  public void removeFavoriteRecipe(Recipe recipe) {
    favoriteRecipes.remove(recipe);
  }

  public boolean isAdmin() {
    return Arrays.asList(roles).contains("ADMIN");
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", username='" + username + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(name, user.name) &&
        Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, username);
  }
}
