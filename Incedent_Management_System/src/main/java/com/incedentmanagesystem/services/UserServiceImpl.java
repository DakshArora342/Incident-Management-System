package com.incedentmanagesystem.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incedentmanagesystem.entities.Incident;
import com.incedentmanagesystem.entities.User;
import com.incedentmanagesystem.extras.OTPGenerator;
import com.incedentmanagesystem.extras.SendEmail;
import com.incedentmanagesystem.repositories.IncidentRepo;
import com.incedentmanagesystem.repositories.UserRepo;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private IncidentRepo incidentRepo;

	@Override
	public User addUser(User user) {
		User user2 = null;
		Optional<User> optional = userRepo.findByEmail(user.getEmail());
		if (optional.isPresent()) {
			user2 = optional.get();
		}
		if (user2 == null) {
			User save = userRepo.save(user);
			return save;
		} 
		return null;
	}

	@Override
	public User loginUser(User user) {
		User user2 = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		return user2;
	}

	@Override
	public User forgetPass(User user) {
		User user2;
		Optional<User> optional = userRepo.findByEmail(user.getEmail());
		if(optional.isPresent()) {
			user2=optional.get();
			 SendEmail.sendEmail(user.getEmail(), user2.getPassword());
			 return user2;
		}
		return null;
	}

	@Override
	public User getUserById(String id) {
		User user;
		Optional<User> optional = userRepo.findById(id);
		if(optional.isPresent()) {
			user=optional.get();
			return user;
		}
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public List<Incident> getAllIncidents() {
		// TODO Auto-generated method stub
		return incidentRepo.findAll();
	}

	@Override
	public Incident getIncidentById(String id) {
		Incident incident;
		Optional<Incident> optional = incidentRepo.findById(id);
		if(optional.isPresent()) {
			incident=optional.get();
			return incident;
		}
		return null;
	}

	@Override
	public User findByIncidents(Incident incidents) {
		ArrayList<Incident> arrayList = new ArrayList<>();
		arrayList.add(incidents);
		List<User> list = userRepo.findByIncidents(arrayList);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	

}
