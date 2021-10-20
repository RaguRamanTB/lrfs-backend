package devacademy.rt086300.labreportfollowupsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabReportFollowUpSystemApplication {

	private static final Logger log = LoggerFactory.getLogger(LabReportFollowUpSystemApplication.class);
	
	public static void main(String[] args) {
		log.info("Lab Report Follow-Up System - Spring Boot Application has started.");
		SpringApplication.run(LabReportFollowUpSystemApplication.class, args);
	}

}
