package com.valencra.recipes.repository;

import com.valencra.recipes.model.Step;
import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
}
