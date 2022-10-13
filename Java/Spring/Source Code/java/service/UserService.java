package com.mkyong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkyong.model.User;
import com.mkyong.repos.UserRepository;
import com.mkyong.repos.RepoFactory;

@Service
public class UserService {
	@Autowired
	RepoFactory repoFactory;
	
	public List<User> findAll() {
		List<User> exs = new ArrayList<User>();
		UserRepository exRepo = (UserRepository) repoFactory.getRepository(RepoFactory.Type.USER);
		exRepo.findAll().forEach(exs::add);
		return exs;
	}

	public void delete(int userid) {
		UserRepository exRepo = (UserRepository) repoFactory.getRepository(RepoFactory.Type.USER);
		exRepo.delete(userid);
	}

	public void update(User user) {
		UserRepository exRepo = (UserRepository) repoFactory.getRepository(RepoFactory.Type.USER);
		exRepo.save(user);
	}

	public void create(User user) {
		UserRepository exRepo = (UserRepository) repoFactory.getRepository(RepoFactory.Type.USER);
		exRepo.save(user);
	}
}
