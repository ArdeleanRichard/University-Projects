package com.mkyong.repos;

import org.springframework.data.repository.CrudRepository;

import com.mkyong.model.Exercise;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

}
