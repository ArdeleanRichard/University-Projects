package com.mkyong.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workout")
public class Workout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workoutid")
	private int workoutid;
	
	@ManyToOne
	@JoinColumn(name = "exerciseid", nullable = false)
	private Exercise exercise;
	
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	private User user;
	
	public Workout() {
	}

	public Workout(int workoutid, Exercise exercise, User user) {
		super();
		this.workoutid = workoutid;
		this.exercise = exercise;
		this.user = user;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
