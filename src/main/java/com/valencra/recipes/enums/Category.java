package com.valencra.recipes.enums;

public enum Category {
  BREAKFAST ("Breakfast"),
  LUNCH    ("Lunch"),
  DINNER    ("Dinner"),
  DESSERT   ("Dessert");

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