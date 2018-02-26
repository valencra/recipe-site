package com.valencra.recipes.recipe;

import com.valencra.recipes.core.BaseEntity;
import com.valencra.recipes.ingredient.Ingredient;
import com.valencra.recipes.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Recipe extends BaseEntity {

  private String name;

  private String description;

  private Category category;

  @Lob
  private byte[] image;

  @ManyToMany
  private List<Ingredient> ingredients;

  @ElementCollection
  private List<String> instructions;

  private Integer preparationTime;

  private Integer cookingTime;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  protected Recipe() {
    super();
    ingredients = new ArrayList<>();
    instructions = new ArrayList<>();
  }

  public Recipe(String name, String description, Category category, byte[] image,
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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
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

  public List<String> getInstructions() {
    return instructions;
  }

  public void setInstructions(List<String> instructions) {
    this.instructions = instructions;
  }

  public void addInstruction(String instruction) {
    instructions.add(instruction);
  }

  public void removeInstruction(String instruction) {
    instructions.remove(instruction);
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
        ", instructions=" + instructions +
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
        category == recipe.category &&
        Arrays.equals(image, recipe.image) &&
        Objects.equals(ingredients, recipe.ingredients) &&
        Objects.equals(instructions, recipe.instructions) &&
        Objects.equals(preparationTime, recipe.preparationTime) &&
        Objects.equals(cookingTime, recipe.cookingTime) &&
        Objects.equals(author, recipe.author);
  }

  @Override
  public int hashCode() {

    int
        result =
        Objects.hash(name, description, category, ingredients, instructions, preparationTime,
            cookingTime, author);
    result = 31 * result + Arrays.hashCode(image);
    return result;
  }
}
