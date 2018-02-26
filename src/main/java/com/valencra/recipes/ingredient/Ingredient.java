package com.valencra.recipes.ingredient;

import com.valencra.recipes.core.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Ingredient extends BaseEntity {

  private String name;

  private String condition;

  private Double quantity;

  private String measurement;

  protected Ingredient() {
    super();
  }

  public Ingredient(String name, String condition, Double quantity, String measurement) {
    this.name = name;
    this.condition = condition;
    this.quantity = quantity;
    this.measurement = measurement;
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

  public String getMeasurement() {
    return measurement;
  }

  public void setMeasurement(String measurement) {
    this.measurement = measurement;
  }
}
