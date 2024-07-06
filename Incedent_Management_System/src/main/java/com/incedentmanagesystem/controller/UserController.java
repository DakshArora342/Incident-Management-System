package com.incedentmanagesystem.controller;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.incedentmanagesystem.entities.Incident;
import com.incedentmanagesystem.entities.User;
import com.incedentmanagesystem.extras.IncidentIdGenerator;
import com.incedentmanagesystem.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
    private RestTemplate restTemplate;

 public String getPincodeDetails(String pincode) {
    String url = "http://www.postalpincode.in/api/pincode/" + pincode;

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    String responseBody = responseEntity.getBody();
    if (responseBody != null) {
        return parseCityAndCountry(responseBody);
    } else {
        return "Error: Empty response";
    }
}

private String parseCityAndCountry(String response) {
    try {
        JSONObject responseObject = new JSONObject(response);
        String status = responseObject.getString("Status");

        if ("Success".equals(status)) {
            JSONArray postOfficeArray = responseObject.getJSONArray("PostOffice");
            if (postOfficeArray.length() > 0) {
                JSONObject firstPostOffice = postOfficeArray.getJSONObject(0);
                String city = firstPostOffice.getString("District");
                String country = firstPostOffice.getString("Country");
                return city + ", " + country;
            } else {
                return "No post office found";
            }
        } else {
            return "Invalid pin code";
        }
    } catch (JSONException e) {
        e.printStackTrace();
        return "Error parsing response";
    }
}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		String pincodeDetails = getPincodeDetails(user.getPincode());
		String[] details = pincodeDetails.split(",");
		user.setCity(details[0]);
		user.setCountry(details[1]);
		User user2 = userService.addUser(user);
		if(user2!=null) {
			return new ResponseEntity<User>(user2,HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().body("Email already registered");
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		User loginUser = userService.loginUser(user);
		if(loginUser!=null) {
			return ResponseEntity.ok("Logged in successful");
		}
		return ResponseEntity.badRequest().body("Invalid  Email or Password");
	}
	
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestBody User user){
		User user2 = userService.forgetPass(user);
		if(user2!=null) {
			return ResponseEntity.ok("Password sent to your email");
		}
		return ResponseEntity.badRequest().body("Invalid  Email");
	}

	@PostMapping("createIncident/{userId}")
	public ResponseEntity<?> createIncident(@PathVariable String userId, @RequestBody Incident incident){
	    // Retrieve the User from the database using the userId
	    User user = userService.getUserById(userId);
	    if (user == null) {
	        return ResponseEntity.badRequest().body("User not found");
	    }
	    incident.setId(IncidentIdGenerator.generateIncidentId());
	    
	    incident.setReportedDateTime(new Date());
	    List<Incident> incidents = user.getIncidents();
	    incidents.add(incident);
	    user.setIncidents(incidents);
	  
	    userService.updateUser(user);
	    if (user != null) {
	        return ResponseEntity.ok(user.getIncidents());
	    } else {
	        return ResponseEntity.badRequest().body("Failed to create incident");
	    }
	}
	
	@GetMapping("viewAllIncidents")
	public ResponseEntity<?> viewAllIncidents(){
        return ResponseEntity.ok(userService.getAllIncidents());
	}
	
	
	@PutMapping("editIncident/{incidentId}")
	public ResponseEntity<?> editIncident(@PathVariable String incidentId, @RequestBody Incident incident){
		Incident incidentById = userService.getIncidentById(incidentId);
		
		if(incidentById!=null) {
			incidentById.setType(incident.getType());
			incidentById.setReporterName(incident.getReporterName());
			incidentById.setDetails(incident.getDetails());
			incidentById.setPriority(incident.getPriority());
			incidentById.setStatus(incident.getStatus());
			incidentById.setReportedDateTime(new Date());
			User user = userService.findByIncidents(incidentById);
			 List<Incident> incidents = user.getIncidents();
			    incidents.add(incidentById);
			    user.setIncidents(incidents);
			    userService.updateUser(user);
		        return ResponseEntity.ok("Incident Successfully Updated");

		}
        return ResponseEntity.badRequest().body("Invalid incidentId");
	}
	
	

}
