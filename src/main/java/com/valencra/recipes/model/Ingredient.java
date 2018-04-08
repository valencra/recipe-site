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
        Objects.equals(quantity, that.quantity) &&
        Objects.equals(measurement, that.measurement);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, condition, quantity, measurement);
  }
}
