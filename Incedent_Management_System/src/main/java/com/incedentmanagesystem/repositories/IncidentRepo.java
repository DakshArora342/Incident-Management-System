package com.incedentmanagesystem.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.incedentmanagesystem.entities.Incident;
import com.incedentmanagesystem.entities.User;

public interface IncidentRepo extends JpaRepository<Incident, String>{
	
}
