package com.incedentmanagesystem.extras;

import java.util.Random;
import java.time.Year;

public class IncidentIdGenerator {
    public static String generateIncidentId() {
        // Generate a random 5-digit number
        Random rand = new Random();
        int randomNumber = rand.nextInt(90000) + 10000; // Random number between 10000 and 99999

        // Get the current year
        int currentYear = Year.now().getValue();

        // Construct the Incident ID
        String incidentId = "RMG" + randomNumber + currentYear;

        return incidentId;
    }
}
