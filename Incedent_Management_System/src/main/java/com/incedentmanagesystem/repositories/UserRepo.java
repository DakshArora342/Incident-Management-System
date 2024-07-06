package com.incedentmanagesystem.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.incedentmanagesystem.entities.User;
import java.util.List;
import com.incedentmanagesystem.entities.Incident;


public interface UserRepo extends JpaRepository<User, String>{
	public Optional<User> findByEmail(String email);
	public User findByEmailAndPassword(String email, String password);
	public List<User> findByIncidents(List<Incident> incidents);
}
