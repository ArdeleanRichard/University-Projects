package com.mkyong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkyong.model.Admin;
import com.mkyong.repos.AdminRepository;
import com.mkyong.repos.RepoFactory;

@Service
public class AdminService {
	@Autowired
	RepoFactory repoFactory;

	public List<Admin> findAll() {
		List<Admin> admins = new ArrayList<>();
		AdminRepository adminRepo = (AdminRepository) repoFactory.getRepository(RepoFactory.Type.ADMIN);
		adminRepo.findAll().forEach(admins::add);
		return admins;
	}
}
