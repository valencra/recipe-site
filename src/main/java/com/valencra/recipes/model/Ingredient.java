package com.valencra.recipes.model;

import com.valencra.recipes.model.BaseEntity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Ingredient extends BaseEntity {

  private String name;

  private String condition;

  private Double quantity;

  public Ingredient(String name, String condition, Double quantity) {
    this.name = name;
    this.condition = condition;
    this.quantity = quantity;
  }

  public Ingredient()
  {
    this(null, null, null);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ingredient that = (Ingredient) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(condition, that.condition) &&
        Objects.equals(quantity, that.quantity);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, condition, quantity);
  }
}
