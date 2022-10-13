package com.mkyong.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercise")
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exerciseid")
	public int exerciseid;
	@Column(name = "name")
	public String name;
	@Column(name = "muscle")
	public String muscle;
	@Column(name = "difficulty")
	public int difficulty;
	
	
	public Exercise() {
	}


	public Exercise(int id, String name, String muscle, int difficulty) {
		super();
		this.exerciseid = id;
		this.name = name;
		this.muscle = muscle;
		this.difficulty = difficulty;
	}


	public int getExerciseid() {
		return exerciseid;
	}


	public void setExerciseid(int id) {
		this.exerciseid = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMuscle() {
		return muscle;
	}


	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
}
