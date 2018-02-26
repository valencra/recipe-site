package com.valencra.recipe.recipe;

public enum Category {
  APPETIZER ("Appetizer"),
  ENTREE    ("Entree"),
  DESSERT   ("Dessert"),
  DRINK     ("Drink");

  private String name;

  Category(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}