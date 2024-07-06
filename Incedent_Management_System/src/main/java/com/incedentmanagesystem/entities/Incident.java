package com.incedentmanagesystem.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
	
	@Id
	private String id;
    private String type; 
    private String reporterName;
    private String details;
    private Date reportedDateTime;
    private String priority; 
    private String status; 

}
