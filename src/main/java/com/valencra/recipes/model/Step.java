package com.valencra.recipes.model;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class Step extends BaseEntity {
  private String name;

  public Step(String name) {
    this.name = name;
  }

  public Step()
  {
    this(null);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Step step = (Step) o;
    return Objects.equals(name, step.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name);
  }
}
