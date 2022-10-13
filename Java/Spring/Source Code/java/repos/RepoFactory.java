package com.mkyong.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RepoFactory {
	public enum Type{
		USER,
		ADMIN,
		EXERCISE,
	}
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	ExerciseRepository exerciseRepo;
	
	public CrudRepository<?,?> getRepository(Type repType){
		switch(repType){
			case USER:
				return userRepo;
			case ADMIN:
				return adminRepo;
			case EXERCISE:
				return exerciseRepo;
			default:
				throw new IllegalArgumentException("Invalid factory");
		}
	}
}
