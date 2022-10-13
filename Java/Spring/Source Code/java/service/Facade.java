package com.mkyong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mkyong.model.Admin;
import com.mkyong.model.Exercise;
import com.mkyong.model.User;

@Component
public class Facade {
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ExerciseService exerciseService;
	
	public Facade(){
		
	}
	
	public List<User> findAllUsers(){
		return userService.findAll();
	}
	
	public List<Admin> findAllAdmins(){
		return adminService.findAll();
	}
	
	public List<Exercise> findAllExercises(){
		return exerciseService.findAll();
	}

	public void createExercise(Exercise ex) {
		exerciseService.create(ex);
	}

	public void deleteExercise(int exerciseid) {
		exerciseService.delete(exerciseid);
	}

	public void updateExercise(Exercise exercise) {
		exerciseService.update(exercise);
		
	}

	public void createUser(User u) {
		userService.create(u);
		
	}

	public void deleteUser(int userid) {
		userService.delete(userid);
		
	}

	public void updateUser(User u) {
		userService.update(u);
		
	}
	
}
