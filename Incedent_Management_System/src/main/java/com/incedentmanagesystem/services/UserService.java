package com.incedentmanagesystem.services;

import java.util.List;

import com.incedentmanagesystem.entities.Incident;
import com.incedentmanagesystem.entities.User;

public interface UserService {
	
	public User addUser(User user);
	
	public User loginUser(User user);
	public User forgetPass(User user);
	public User getUserById(String id);
	public User updateUser(User user);
	public List<Incident> getAllIncidents();
	public Incident getIncidentById(String id);
	public User findByIncidents(Incident incidents);


//	
//	User forgetPassword(String id);
//	
	

}
