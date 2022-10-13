package com.mkyong.repos;

import org.springframework.data.repository.CrudRepository;

import com.mkyong.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
