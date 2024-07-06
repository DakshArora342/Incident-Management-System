package com.incedentmanagesystem.entities;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String mobile;
	
private String pincode;
	
	private String city;
	
	private String country;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<Incident> incidents;


}
