package com.mkyong.repos;

import org.springframework.data.repository.CrudRepository;

import com.mkyong.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

}
