package com.valencra.recipes.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Recipe extends BaseEntity {

  private String name;

  private String description;

  private String category;

  @Lob
  private byte[] image;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "recipe_id")
  private List<Ingredient> ingredients;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "recipe_id")
  private List<Step> steps;

  private Integer preparationTime;

  private Integer cookingTime;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  public Recipe() {
    super();
    ingredients = new ArrayList<>();
    steps = new ArrayList<>();
  }

  public Recipe(String name, String description, String category, byte[] image,
                Integer preparationTime, Integer cookingTime, User author) {
    this();
    this.name = name;
    this.description = description;
    this.category = category;
    this.image = image;
    this.preparationTime = preparationTime;
    this.cookingTime = cookingTime;
    this.author = author;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }

  public void removeIngredient(Ingredient ingredient) {
    ingredients.remove(ingredient);
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public void addStep(Step step) {
    steps.add(step);
  }

  public void removeStep(Step step) {
    steps.remove(step);
  }

  public Integer getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(Integer preparationTime) {
    this.preparationTime = preparationTime;
  }

  public Integer getCookingTime() {
    return cookingTime;
  }

  public void setCookingTime(Integer cookingTime) {
    this.cookingTime = cookingTime;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public boolean isFavorite(User user) {
    return user.getFavoriteRecipes().contains(this);
  }

  @Override
  public String toString() {
    return "Recipe{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", category=" + category +
        ", image=" + Arrays.toString(image) +
        ", ingredients=" + ingredients +
        ", steps=" + steps +
        ", preparationTime=" + preparationTime +
        ", cookingTime=" + cookingTime +
        ", author=" + author +
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
    Recipe recipe = (Recipe) o;
    return Objects.equals(name, recipe.name) &&
        Objects.equals(description, recipe.description) &&
        category.equals(recipe.category) &&
        Arrays.equals(image, recipe.image) &&
        Objects.equals(ingredients, recipe.ingredients) &&
        Objects.equals(steps, recipe.steps) &&
        Objects.equals(preparationTime, recipe.preparationTime) &&
        Objects.equals(cookingTime, recipe.cookingTime) &&
        Objects.equals(author, recipe.author);
  }

  @Override
  public int hashCode() {

    int
        result =
        Objects.hash(name, description, category, ingredients, steps, preparationTime,
            cookingTime, author);
    result = 31 * result + Arrays.hashCode(image);
    return result;
  }
}
