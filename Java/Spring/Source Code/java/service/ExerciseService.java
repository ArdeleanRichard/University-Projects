package com.mkyong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkyong.model.Exercise;
import com.mkyong.repos.ExerciseRepository;
import com.mkyong.repos.RepoFactory;

@Service
public class ExerciseService {
	@Autowired
	RepoFactory repoFactory;
	
	public List<Exercise> findAll() {
		List<Exercise> exs = new ArrayList<Exercise>();
		ExerciseRepository exRepo = (ExerciseRepository) repoFactory.getRepository(RepoFactory.Type.EXERCISE);
		exRepo.findAll().forEach(exs::add);
		return exs;
	}

	public void delete(int exerciseid) {
		ExerciseRepository exRepo = (ExerciseRepository) repoFactory.getRepository(RepoFactory.Type.EXERCISE);
		exRepo.delete(exerciseid);
	}

	public void update(Exercise exercise) {
		ExerciseRepository exRepo = (ExerciseRepository) repoFactory.getRepository(RepoFactory.Type.EXERCISE);
		exRepo.save(exercise);
	}

	public void create(Exercise ex) {
		ExerciseRepository exRepo = (ExerciseRepository) repoFactory.getRepository(RepoFactory.Type.EXERCISE);
		exRepo.save(ex);
	}

}
